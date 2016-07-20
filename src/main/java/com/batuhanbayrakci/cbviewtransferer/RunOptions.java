package com.batuhanbayrakci.cbviewtransferer;

import org.apache.commons.cli.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;

public class RunOptions {

    private List<String> bucketNames;

    private URI sourceURI;

    private URI targetURI;

    private String username;

    private String password;

    public RunOptions(String args[]) throws ParseException {
        org.apache.commons.cli.Options options = new org.apache.commons.cli.Options();

        options.addOption("buckets", true, "comma-separated bucket names that wanted to be transfered. " +
                "Example: Products,Sellers,Customers");
        options.addOption("source", true, "source node address");
        options.addOption("target", true, "target node address");
        options.addOption("username", true, "username of web admin panel");
        options.addOption("password", true, "password of web admin panel");

        CommandLine commandLine = new DefaultParser().parse(options, args);
        this.bucketNames = parseBucketNames(commandLine, "buckets");
        this.sourceURI = parseNodeURI(commandLine, "source");
        this.targetURI = parseNodeURI(commandLine, "target");
        if (sourceURI.equals(targetURI)) {
            throw new RuntimeException("Source and target URIs cannot be same.");
        }
        this.username = commandLine.getOptionValue("username", "");
        this.password = commandLine.getOptionValue("password", "");
    }

    private List<String> parseBucketNames(CommandLine commandLine, String optionName) throws MissingArgumentException {
        return Arrays.asList(commandLine.getOptionValue(optionName).split(","));
    }

    private URI parseNodeURI(CommandLine commandLine, String optionName) throws MissingArgumentException {
        String rawUri = commandLine.getOptionValue(optionName);
        try {
            URI uri = new URI(rawUri);
            if (!uri.getPath().equalsIgnoreCase("/pools")) {
                return new URI(uri.getScheme(), null, uri.getHost(), uri.getPort(), "/pools", null, null);
            }
            return uri;

        } catch (URISyntaxException e) {
            throw new MissingArgumentException("Missing or corrupt node '"+ optionName +"' URI:" + rawUri);
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

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
