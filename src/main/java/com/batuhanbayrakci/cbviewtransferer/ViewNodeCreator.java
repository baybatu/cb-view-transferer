package com.batuhanbayrakci.cbviewtransferer;

import com.batuhanbayrakci.cbviewtransferer.model.*;
import com.couchbase.client.CouchbaseClient;
import com.couchbase.client.CouchbaseConnectionFactoryBuilder;
import com.couchbase.client.protocol.views.DesignDocument;
import com.couchbase.client.protocol.views.SpatialViewDesign;
import com.couchbase.client.protocol.views.ViewDesign;

import java.io.IOException;
import java.net.URI;
import java.util.Collections;
import java.util.concurrent.TimeUnit;

public class ViewNodeCreator implements ViewCreator {

    public void create(Buckets buckets, URI targetURI) throws IOException {
        for (Bucket bucket : buckets.getBuckets()) {
            if (bucket.getDesignDocs().isEmpty()) {
                System.out.println("There is no design doc definition for '" + bucket.getName() + "' bucket. Skipping...");
                continue;
            }

            CouchbaseClient couchbaseClient;
            try {
                System.out.println("Couchbase client is being created for '" + bucket.getName() + "' bucket.");
                couchbaseClient = new CouchbaseClient(
                        new CouchbaseConnectionFactoryBuilder()
                                .buildCouchbaseConnection(Collections.singletonList(targetURI), bucket.getName(), ""));
            } catch (Exception e) {
                System.out.println("Connection error occurred while connecting to bucket '" + bucket.getName() + "'. " +
                        "Skipping this bucket...");
                continue;
            }

            System.out.println("Couchbase client has been initialized for bucket '" + bucket.getName() + "'");
            System.out.println("Scanning design documents...");

            for (DesignDoc designDoc : bucket.getDesignDocs()) {
                if (createDesignDoc(couchbaseClient, designDoc)) {
                    System.out.println("Design document '" + designDoc.getName() + "' created successfully.");
                } else {
                    System.out.println("Design document '" + designDoc.getName() + "' could not be created.");
                }
            }

            System.out.println("Couchbase client is being shut down for '" + bucket.getName() + "' bucket.");
            couchbaseClient.shutdown(10, TimeUnit.SECONDS);
        }
    }

    private static boolean createDesignDoc(CouchbaseClient couchbaseClient, DesignDoc designDoc) {
        DesignDocument newDesignDoc = new DesignDocument(designDoc.getName());
        for (MapReduceView mapReduceView : designDoc.getMapReduceViews()) {
            newDesignDoc.getViews().add(
                    new ViewDesign(mapReduceView.getName(), mapReduceView.getMapFunction(), mapReduceView.getReduceValue()));
        }
        for (SpatialView spatialView : designDoc.getSpatialViews()) {
            newDesignDoc.getSpatialViews().add(
                    new SpatialViewDesign(spatialView.getName(), spatialView.getMapFunction()));
        }
        return couchbaseClient.createDesignDoc(newDesignDoc);
    }
}
