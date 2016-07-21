package com.batuhanbayrakci.cbviewtransferer.model;

import com.batuhanbayrakci.cbviewtransferer.jsbeautify.JsBeautify;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlAccessorType(XmlAccessType.FIELD)
public class MapReduceView {

    @XmlAttribute(name = "name", required = true)
    private String name;

    @XmlElement(name = "map-function", required = true)
    @XmlJavaTypeAdapter(CDataAdapter.class)
    private String mapFunction;

    @XmlElement(name = "reduce")
    private Reduce reduce = new Reduce();

    public static MapReduceView create(String name, String mapFunction, String reduce) {
        MapReduceView mapReduceView = new MapReduceView();
        mapReduceView.setName(name);
        mapReduceView.setMapFunction(JsBeautify.beautify(mapFunction));
        Reduce reduceObject = new Reduce();
        reduceObject.setValue(reduce);
        mapReduceView.setReduce(reduceObject);
        return mapReduceView;
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

    public Reduce getReduce() {
        return reduce;
    }

    public void setReduce(Reduce reduce) {
        this.reduce = reduce;
    }

    public String getReduceValue() {
        return getReduce().getValue();
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    public static class Reduce {

        @XmlAttribute(name = "value")
        private String value = "";

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }

}
