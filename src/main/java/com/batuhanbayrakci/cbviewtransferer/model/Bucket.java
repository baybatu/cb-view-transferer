package com.batuhanbayrakci.cbviewtransferer.model;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
public class Bucket {

    @XmlAttribute(name = "name", required = true)
    private String name;

    @XmlElement(name = "design-doc", required = true)
    private List<DesignDoc> designDocs = new ArrayList<>();

    public Bucket() {
    }

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