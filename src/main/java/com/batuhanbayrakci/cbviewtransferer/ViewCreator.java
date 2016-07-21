package com.batuhanbayrakci.cbviewtransferer;

import com.batuhanbayrakci.cbviewtransferer.model.Buckets;

import java.io.IOException;
import java.net.URI;

public interface ViewCreator {

    void create(Buckets buckets, URI targetURI) throws IOException;

}
