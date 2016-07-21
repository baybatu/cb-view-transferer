package com.batuhanbayrakci.cbviewtransferer.model;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Buckets {

    @XmlElement(name = "bucket", required = true)
    private List<Bucket> buckets = new ArrayList<Bucket>();

    public List<Bucket> getBuckets() {
        return buckets;
    }

    public void setBuckets(List<Bucket> buckets) {
        this.buckets = buckets;
    }

    public void addBucket(Bucket cbBucket) {
        buckets.add(cbBucket);
    }

    public boolean hasBucket() {
        return !buckets.isEmpty();
    }

    public Buckets filter(List<String> bucketNames) {
        if (bucketNames.isEmpty()) {
            return this;
        }
        setBuckets(getBuckets().stream()
                .filter(p -> bucketNames.contains(p.getName()))
                .collect(Collectors.toList()));
        return this;
    }

    public Buckets validate() {
        for (Bucket bucket : getBuckets()) {
            for (DesignDoc designDoc : bucket.getDesignDocs()) {
                if (designDoc.isNotValid()) {
                    throw new RuntimeException("Invalid view definition " +
                            "for '" + designDoc.getName() + "' design document in '" + bucket.getName() + "' bucket. " +
                            "Either MapReduce or Spatial Views must be empty.");
                }
            }
        }
        return this;
    }
}
