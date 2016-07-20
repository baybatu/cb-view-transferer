package com.batuhanbayrakci.cbviewtransferer.model;

import java.util.ArrayList;
import java.util.List;

public class Buckets {

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
}
