package com.batuhanbayrakci.cbviewtransferer;

import org.apache.commons.cli.*;
import org.apache.commons.lang3.StringUtils;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RunOptions {

    private List<String> bucketNames;

    private URI sourceURI;

    private URI targetURI;

    private String username;

    private String password;

    private Loader loader;

    private ViewCreator viewCreator;

    private RunOptions(Options options, String args[]) throws ParseException {
        CommandLine commandLine = new DefaultParser().parse(options, args);
        this.bucketNames = parseBucketNames(commandLine, "buckets");

        this.sourceURI = parseURI(commandLine, "source");
        this.targetURI = parseURI(commandLine, "target");

        this.username = commandLine.getOptionValue("username", "");
        this.password = commandLine.getOptionValue("password", "");

        this.loader = getBucketLoaderStrategy();
        this.viewCreator = getViewCreatorStrategy();
    }

    public static RunOptions createFrom(String[] args) throws ParseException {
        org.apache.commons.cli.Options options = new org.apache.commons.cli.Options();

        options.addOption("buckets", true, "comma-separated bucket names that wanted to be transferred. " +
                "Example: Customers,Orders");
        options.addOption("source", true, "Source node address or file path");
        options.addOption("target", true, "Target node address or file path");
        options.addOption("username", true, "Username of Couchbase web admin panel");
        options.addOption("password", true, "Password of Couchbase web admin panel");

        if (args.length == 0 || args[0].endsWith("help")) {
            printHelp(options);
            return null;
        }

        RunOptions runOptions = new RunOptions(options, args);
        runOptions.validateParameters();
        return runOptions;
    }

    private void validateParameters() {
        if (sourceURI.equals(targetURI)) {
            throw new RuntimeException("Source and target URIs cannot be same.");
        }
        if (isNodeAddress(sourceURI) && (StringUtils.isAnyBlank(username, password))) {
            throw new RuntimeException("Couchbase admin panel username and password is mandatory.");
        }
    }

    public static void printHelp(Options options) {
        HelpFormatter helpFormatter = new HelpFormatter();
        helpFormatter.printHelp("cb-view-transfer", options, true);
    }

    private List<String> parseBucketNames(CommandLine commandLine, String optionName) throws MissingArgumentException {
        String buckets = commandLine.getOptionValue(optionName);
        if (StringUtils.isBlank(buckets)) {
            return new ArrayList<>();
        }
        return Arrays.asList(buckets.split(","));
    }

    private URI parseURI(CommandLine commandLine, String optionName) throws MissingArgumentException {
        String rawUri = commandLine.getOptionValue(optionName);
        try {
            URI uri = new URI(rawUri);
            if (isNodeAddress(uri)) {
                if (!uri.getPath().equalsIgnoreCase("/pools")) {
                    return new URI(uri.getScheme(), null, uri.getHost(), uri.getPort(), "/pools", null, null);
                }
            }
            return uri;
        } catch (URISyntaxException e) {
            throw new MissingArgumentException("Missing or corrupt uri: '" + rawUri + "'");
        }
    }

    public List<String> getBucketNames() {
        return bucketNames;
    }

    public URI getSourceURI() {
        return sourceURI;
    }

    public URI getTargetURI() {
        return targetURI;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public Loader getLoader() {
        return loader;
    }

    public ViewCreator getViewCreator() {
        return viewCreator;
    }

    private ViewCreator getViewCreatorStrategy() {
        return isNodeAddress(targetURI) ? new ViewNodeCreator() : new ViewXMLFileCreator();
    }

    private Loader getBucketLoaderStrategy() {
        return isNodeAddress(sourceURI) ? new ViewNodeLoader() : new ViewXMLFileLoader();
    }

    private boolean isNodeAddress(URI uri) {
        return uri.toString().matches("^http(s)?://.*");
    }
}
