package com.batuhanbayrakci.cbviewtransferer;

import com.batuhanbayrakci.cbviewtransferer.model.Buckets;
import org.apache.commons.cli.ParseException;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws Exception {
        RunOptions runOptions = parseRunOptions(args);
        if (runOptions == null) {
            return;
        }
        Buckets buckets = new CouchbaseViewLoader().loadFrom(runOptions.getSourceURI(),
                runOptions.getBucketNames(),
                runOptions.getUsername(),
                runOptions.getPassword());
        restoreViews(runOptions, buckets);
    }

    private static void restoreViews(RunOptions runOptions, Buckets buckets) throws IOException {
        new CouchbaseViewRestorer().restore(buckets, runOptions.getTargetURI());
    }

    private static RunOptions parseRunOptions(String[] args) throws Exception {
        try {
            return new RunOptions(args);
        } catch (ParseException e) {
            System.err.println(e.getMessage());
            return null;
        } catch (Exception e) {
            System.err.println(e.getMessage());
            throw e;
        }
    }

}
