package com.batuhanbayrakci.cbviewtransferer;

import com.batuhanbayrakci.cbviewtransferer.model.Buckets;
import org.apache.commons.cli.ParseException;

public class Main {

    public static void main(String[] args) throws Exception {
        RunOptions runOptions = parseRunOptions(args);
        if (runOptions == null) {
            return;
        }

        Buckets buckets = runOptions.getLoader().load(new ViewLoaderParameters(runOptions));
        if (buckets.hasBucket()) {
            runOptions.getViewCreator().create(buckets, runOptions.getTargetURI());
        } else {
            System.out.println("There is no bucket to transfer.");
        }
    }

    private static RunOptions parseRunOptions(String[] args) throws Exception {
        try {
            return RunOptions.createFrom(args);
        } catch (ParseException e) {
            System.err.println(e.getMessage());
            return null;
        } catch (Exception e) {
            System.err.println(e.getMessage());
            throw e;
        }
    }

}
