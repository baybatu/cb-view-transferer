package com.batuhanbayrakci.cbviewtransferer;

import com.batuhanbayrakci.cbviewtransferer.model.Buckets;
import org.apache.commons.cli.ParseException;

import java.util.Optional;

public class Main {

    public static void main(String[] args) throws Exception {
        Optional<RunOptions> runOptions = parseRunOptions(args);
        if (!runOptions.isPresent()) {
            return;
        }

        RunOptions options = runOptions.get();
        Buckets buckets = options.getLoader().load(new ViewLoaderParameters(options));
        if (buckets.hasBucket()) {
            options.getViewCreator().create(buckets, options.getTargetURI());
        } else {
            System.out.println("There is no bucket to transfer.");
        }
    }

    private static Optional<RunOptions> parseRunOptions(String[] args) throws Exception {
        try {
            return RunOptions.createFrom(args);
        } catch (ParseException e) {
            System.err.println(e.getMessage());
            return Optional.empty();
        } catch (Exception e) {
            System.err.println(e.getMessage());
            throw e;
        }
    }

}
