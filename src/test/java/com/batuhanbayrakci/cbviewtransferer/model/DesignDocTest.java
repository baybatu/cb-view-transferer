package com.batuhanbayrakci.cbviewtransferer.model;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class DesignDocTest {

    @Test
    public void shouldDesignDocumentsBeValidIfItHasSpatialViewButMapReduceView() {
        DesignDoc designDoc = new DesignDoc();
        designDoc.setMapReduceViews(new ArrayList<>());
        designDoc.setSpatialViews(Collections.singletonList(new SpatialView()));

        assertTrue(designDoc.isValid());
    }

    @Test
    public void shouldDesignDocumentsBeValidIfItHasMapReduceViewButSpatialView() {
        DesignDoc designDoc = new DesignDoc();
        designDoc.setMapReduceViews(Collections.singletonList(new MapReduceView()));
        designDoc.setSpatialViews(new ArrayList<>());

        assertTrue(designDoc.isValid());
    }

    @Test
    public void shouldDesignDocumentsNotBeValidIfItHasNeitherMapReduceViewNorSpatialView() {
        DesignDoc designDoc = new DesignDoc();
        designDoc.setMapReduceViews(new ArrayList<>());
        designDoc.setSpatialViews(new ArrayList<>());

        assertFalse(designDoc.isValid());
    }

    @Test
    public void shouldDesignDocumentsNotBeValidIfItHasBothMapReduceViewAndSpatialView() {
        DesignDoc designDoc = new DesignDoc();
        designDoc.setMapReduceViews(Collections.singletonList(new MapReduceView()));
        designDoc.setSpatialViews(Collections.singletonList(new SpatialView()));

        assertFalse(designDoc.isValid());
    }

}