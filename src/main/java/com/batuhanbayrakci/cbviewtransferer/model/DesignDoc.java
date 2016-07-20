package com.batuhanbayrakci.cbviewtransferer.model;

import java.util.ArrayList;
import java.util.List;

public class DesignDoc {

    private String name;

    private List<MapReduceView> mapReduceViews = new ArrayList<MapReduceView>();

    private List<SpatialView> spatialViews = new ArrayList<SpatialView>();

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
