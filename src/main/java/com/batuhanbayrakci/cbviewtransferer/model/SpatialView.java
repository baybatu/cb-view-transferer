package com.batuhanbayrakci.cbviewtransferer.model;

import com.batuhanbayrakci.cbviewtransferer.jsbeautify.JsBeautify;

public class SpatialView {

    private String name;

    private String mapFunction;

    public static SpatialView create(String name, String mapFunction) {
        SpatialView spatialView = new SpatialView();
        spatialView.setName(name);
        spatialView.setMapFunction(JsBeautify.beautify(mapFunction));
        return spatialView;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMapFunction() {
        return mapFunction;
    }

    public void setMapFunction(String mapFunction) {
        this.mapFunction = mapFunction;
    }

}
