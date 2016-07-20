package com.batuhanbayrakci.cbviewtransferer.model;

import com.batuhanbayrakci.cbviewtransferer.jsbeautify.JsBeautify;

public class MapReduceView {

    private String name;

    private String mapFunction;

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

    public static class Reduce {

        private String value = "";

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }

}
