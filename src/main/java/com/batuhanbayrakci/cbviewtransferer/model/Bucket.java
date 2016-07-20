package com.batuhanbayrakci.cbviewtransferer.model;

import java.util.ArrayList;
import java.util.List;

public class Bucket {

    private String name;

    private List<DesignDoc> designDocs = new ArrayList<DesignDoc>();

    public Bucket(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<DesignDoc> getDesignDocs() {
        return designDocs;
    }

    public void setDesignDocs(List<DesignDoc> designDocs) {
        this.designDocs = designDocs;
    }

    public void addDesignDoc(DesignDoc designDoc) {
        designDocs.add(designDoc);
    }
}
