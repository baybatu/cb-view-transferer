package com.batuhanbayrakci.cbviewtransferer.model;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
public class DesignDoc {

    @XmlAttribute(name = "name", required = true)
    private String name;

    @XmlElement(name = "view")
    private List<MapReduceView> mapReduceViews = new ArrayList<>();

    @XmlElement(name = "spatial-view")
    private List<SpatialView> spatialViews = new ArrayList<>();

    public static DesignDoc create(String name, List<MapReduceView> mapReduceViews, List<SpatialView> spatialViews) {
        DesignDoc designDoc = new DesignDoc();
        designDoc.setName(name);
        designDoc.setMapReduceViews(mapReduceViews);
        designDoc.setSpatialViews(spatialViews);
        return designDoc;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<MapReduceView> getMapReduceViews() {
        return mapReduceViews;
    }

    public void setMapReduceViews(List<MapReduceView> mapReduceViews) {
        this.mapReduceViews = mapReduceViews;
    }

    public List<SpatialView> getSpatialViews() {
        return spatialViews;
    }

    public void setSpatialViews(List<SpatialView> spatialViews) {
        this.spatialViews = spatialViews;
    }

    public boolean isValid() {
        return getSpatialViews().isEmpty() ^ getMapReduceViews().isEmpty();
    }

    public boolean isNotValid() {
        return !isValid();
    }

    public void addMapReduceView(MapReduceView view) {
        mapReduceViews.add(view);
    }

    public void addSpatialView(SpatialView view) {
        spatialViews.add(view);
    }
}
