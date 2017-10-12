package com.batuhanbayrakci.cbviewtransferer;

import com.batuhanbayrakci.cbviewtransferer.model.Bucket;
import com.batuhanbayrakci.cbviewtransferer.model.Buckets;
import com.batuhanbayrakci.cbviewtransferer.model.DesignDoc;
import com.batuhanbayrakci.cbviewtransferer.model.MapReduceView;
import com.batuhanbayrakci.cbviewtransferer.testrule.OutputCapture;
import org.hamcrest.core.IsEqual;
import org.junit.Rule;
import org.junit.Test;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collections;

public class ViewXMLFileCreatorTest {

    @Rule
    public OutputCapture outputCapture = new OutputCapture();

    @Test
    public void shouldMarshallViewsAsXml() throws URISyntaxException, IOException {
        MapReduceView view = new MapReduceView();
        MapReduceView.Reduce countReduce = new MapReduceView.Reduce();
        view.setName("byId");
        view.setMapFunction("function (doc, meta) {\n\temit (doc.id, null);\n}");
        view.setReduce(countReduce);

        DesignDoc productDesignDoc = new DesignDoc();
        productDesignDoc.setName("productDesignDoc");
        productDesignDoc.setMapReduceViews(Collections.singletonList(view));

        Bucket productBucket = new Bucket("products");
        productBucket.setDesignDocs(Collections.singletonList(productDesignDoc));

        Buckets buckets = new Buckets();
        buckets.setBuckets(Collections.singletonList(productBucket));

        new ViewXMLFileCreator().create(buckets, new URI("/tmp/xml_file_test" + System.currentTimeMillis()));

        outputCapture.expect(new IsEqual<>("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" +
                "<buckets>\n" +
                "    <bucket name=\"products\">\n" +
                "        <design-doc name=\"productDesignDoc\">\n" +
                "            <view name=\"byId\">\n" +
                "                <map-function><![CDATA[function (doc, meta) {\n" +
                "\temit (doc.id, null);\n}]]></map-function>\n" +
                "                <reduce value=\"\"/>\n" +
                "            </view>\n" +
                "        </design-doc>\n" +
                "    </bucket>\n" +
                "</buckets>\n"));
    }
}