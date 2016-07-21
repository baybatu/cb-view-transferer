package com.batuhanbayrakci.cbviewtransferer.model;

import com.batuhanbayrakci.cbviewtransferer.jsbeautify.JsBeautify;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlAccessorType(XmlAccessType.FIELD)
public class SpatialView {

    @XmlAttribute(name = "name", required = true)
    private String name;

    @XmlElement(name = "map-function", required = true)
    @XmlJavaTypeAdapter(CDataAdapter.class)
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
