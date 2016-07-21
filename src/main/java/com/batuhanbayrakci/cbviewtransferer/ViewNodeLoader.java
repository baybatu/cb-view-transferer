package com.batuhanbayrakci.cbviewtransferer;

import com.batuhanbayrakci.cbviewtransferer.model.*;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.core.type.TypeReference;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ViewNodeLoader implements Loader {

    public Buckets load(ViewLoaderParameters viewLoaderParameters) {
        Buckets cbBuckets = new Buckets();
        for (String bucket : getBucketNames(viewLoaderParameters.getBucketNames(), viewLoaderParameters.getSourceURI())) {
            try {
                HttpResponse<JsonNode> response = Unirest
                        .get(viewLoaderParameters.getSourceURI().toString() + "/default/buckets/" + bucket + "/ddocs")
                        .basicAuth(viewLoaderParameters.getUsername(), viewLoaderParameters.getPassword())
                        .asJson();
                Rows rows = new JsonSerializer().fromJson(response.getBody().toString(), Rows.class);
                Bucket cbBucket = new Bucket(bucket);
                for (Row row : rows.getRows()) {
                    String id = row.getDoc().getMeta().getId();
                    if (id.substring(id.lastIndexOf('/')).startsWith("/dev_")) {
                        continue;
                    }
                    String designDocName = id.substring(id.lastIndexOf('/') + 1);

                    List<MapReduceView> mapReduceViews = new ArrayList<>();
                    for (Map.Entry<String, ViewDetail> entry : row.getDoc().getJson().getViews().getProps().entrySet()) {
                        String viewName = entry.getKey();
                        ViewDetail viewDetail = entry.getValue();
                        mapReduceViews.add(MapReduceView.create(viewName, viewDetail.getMap(), viewDetail.getReduce()));
                    }

                    List<SpatialView> spatialViews = new ArrayList<>();
                    for (Map.Entry<String, String> entry : row.getDoc().getJson().getSpatial().getProps().entrySet()) {
                        String viewName = entry.getKey();
                        String mapFunction = entry.getValue();
                        spatialViews.add(SpatialView.create(viewName, mapFunction));
                    }

                    cbBucket.addDesignDoc(DesignDoc.create(designDocName, mapReduceViews, spatialViews));
                }
                cbBuckets.addBucket(cbBucket);

            } catch (UnirestException e) {
                throw new RuntimeException(e);
            }
        }
        return cbBuckets;
    }

    private List<String> getBucketNames(List<String> buckets, URI sourceURI) {
        if (!buckets.isEmpty()) {
            return buckets;
        }
        System.out.println("Fetching all bucket info from node:'" + sourceURI.toString() + "'");
        try {
            HttpResponse<JsonNode> response = Unirest
                    .get(sourceURI.toString() + "/default/buckets")
                    .asJson();
            List<BucketName> allBucketNames = new JsonSerializer()
                    .fromJsonFromTypedReference(
                            response.getBody().toString(),
                            new TypeReference<List<BucketName>>() {});
            return allBucketNames.stream().map(BucketName::getName).collect(Collectors.toList());
        } catch (UnirestException e) {
            throw new RuntimeException(e);
        }
    }
}

class BucketName {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

class Rows {
    private List<Row> rows = new ArrayList<>();

    public List<Row> getRows() {
        return rows;
    }

    public void setRows(List<Row> rows) {
        this.rows = rows;
    }
}

class Row {
    private Doc doc;

    public Doc getDoc() {
        return doc;
    }

    public void setDoc(Doc doc) {
        this.doc = doc;
    }
}

class Doc {
    private Meta meta;
    private Json json;

    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }

    public Json getJson() {
        return json;
    }

    public void setJson(Json json) {
        this.json = json;
    }
}

class Meta {
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}

class Json {
    private View views = new View();
    private SpaView spatial = new SpaView();

    public View getViews() {
        return views;
    }

    public void setViews(View views) {
        this.views = views;
    }

    public SpaView getSpatial() {
        return spatial;
    }

    public void setSpatial(SpaView spatial) {
        this.spatial = spatial;
    }
}

class View {
    private Map<String, ViewDetail> props = new HashMap<String, ViewDetail>();

    public Map<String, ViewDetail> getProps() {
        return props;
    }

    public void setProps(Map<String, ViewDetail> props) {
        this.props = props;
    }

    @JsonAnySetter
    public void add(String key, ViewDetail value) {
        props.put(key, value);
    }
}

class SpaView {
    private Map<String, String> props = new HashMap<String, String>();

    public Map<String, String> getProps() {
        return props;
    }

    public void setProps(Map<String, String> props) {
        this.props = props;
    }

    @JsonAnySetter
    public void add(String key, String value) {
        props.put(key, value);
    }
}

class ViewDetail {
    private String map = "";
    private String reduce = "";

    public String getMap() {
        return map;
    }

    public void setMap(String map) {
        this.map = map;
    }

    public String getReduce() {
        return reduce;
    }

    public void setReduce(String reduce) {
        this.reduce = reduce;
    }
}