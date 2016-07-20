package com.batuhanbayrakci.cbviewtransferer;

import com.batuhanbayrakci.cbviewtransferer.model.*;
import com.couchbase.client.CouchbaseClient;
import com.couchbase.client.CouchbaseConnectionFactoryBuilder;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import java.net.URI;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class CouchbaseViewLoader {

    public Buckets loadFrom(URI sourceURI, List<String> bucketNames, String username, String password) {
        Buckets cbBuckets = new Buckets();
        for (String bucket : bucketNames) {
            CouchbaseClient couchbaseClient;
            try {
                System.out.println("Couchbase client is being created for '" + bucket + "' bucket.");
                couchbaseClient = new CouchbaseClient(
                        new CouchbaseConnectionFactoryBuilder()
                                .buildCouchbaseConnection(Collections.singletonList(sourceURI), bucket, ""));
            } catch (Exception e) {
                System.out.println("Connection error occurred while connecting to bucket '" + bucket + "'. " +
                        "Skipping this bucket...");
                continue;
            }

            System.out.println("Couchbase client has been initialized for bucket '" + bucket + "'");

            try {
                HttpResponse<JsonNode> response = Unirest
                        .get(sourceURI.toString() + "/default/buckets/" + bucket + "/ddocs")
                        .basicAuth(username, password)
                        .asJson();
                Rows rows = new JsonSerializer().fromJson(response.getBody().toString(), Rows.class);
                Bucket cbBucket = new Bucket(bucket);
                for (Row row : rows.getRows()) {
                    String id = row.getDoc().getMeta().getId();
                    if (id.substring(id.lastIndexOf('/')).startsWith("/dev_")) {
                        continue;
                    }
                    String designDocName = id.substring(id.lastIndexOf('/') + 1);

                    List<MapReduceView> mapReduceViews = new ArrayList<MapReduceView>();
                    for (Map.Entry<String, ViewDetail> entry : row.getDoc().getJson().getViews().getProps().entrySet()) {
                        String viewName = entry.getKey();
                        ViewDetail viewDetail = entry.getValue();
                        mapReduceViews.add(MapReduceView.create(viewName, viewDetail.getMap(), viewDetail.getReduce()));
                    }

                    List<SpatialView> spatialViews = new ArrayList<SpatialView>();
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

            System.out.println("Couchbase client is being shut down for '" + bucket + "' bucket.");
            couchbaseClient.shutdown(10, TimeUnit.SECONDS);
        }
        return cbBuckets;
    }
}

class Rows {
    private List<Row> rows = new ArrayList<Row>();

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