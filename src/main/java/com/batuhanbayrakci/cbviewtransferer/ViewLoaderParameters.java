package com.batuhanbayrakci.cbviewtransferer;

import java.net.URI;
import java.util.List;

public class ViewLoaderParameters {

    private URI sourceURI;

    private List<String> bucketNames;

    private String username;

    private String password;

    public ViewLoaderParameters(RunOptions runOptions) {
        this(runOptions.getSourceURI(), runOptions.getBucketNames(), runOptions.getUsername(), runOptions.getPassword());
    }

    public ViewLoaderParameters(URI sourceURI, List<String> bucketNames) {
        this(sourceURI, bucketNames, "", "");
    }

    public ViewLoaderParameters(URI sourceURI, List<String> bucketNames, String username, String password) {
        this.sourceURI = sourceURI;
        this.bucketNames = bucketNames;
        this.username = username;
        this.password = password;
    }

    public URI getSourceURI() {
        return sourceURI;
    }

    public List<String> getBucketNames() {
        return bucketNames;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

}
