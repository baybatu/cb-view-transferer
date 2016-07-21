package com.batuhanbayrakci.cbviewtransferer;

import com.batuhanbayrakci.cbviewtransferer.jsbeautify.JsBeautify;
import com.batuhanbayrakci.cbviewtransferer.model.*;
import org.apache.commons.io.IOUtils;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class ViewXMLFileLoader implements Loader {

    public Buckets load(ViewLoaderParameters loaderParameters) {
        InputStream configFileStream = getConfigFile(loaderParameters.getSourceURI().toString());

        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(Buckets.class);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            unmarshaller.setListener(new FunctionBeautifierListener());
            Buckets buckets = (Buckets) unmarshaller.unmarshal(configFileStream);
            return buckets
                    .filter(loaderParameters.getBucketNames())
                    .validate();
        } catch (Exception e) {
            throw new RuntimeException("Error occurred while parsing config file '" + loaderParameters.getSourceURI().toString() + "'.\n" + e.getMessage());
        } finally {
            IOUtils.closeQuietly(configFileStream);
        }
    }

    private InputStream getConfigFile(String configFile) {
        try {
            return new FileInputStream(configFile);
        } catch (FileNotFoundException e) {
            throw new RuntimeException("Config file '" + configFile + "' not found.");
        }
    }

    private class FunctionBeautifierListener extends Unmarshaller.Listener {
        @Override
        public void afterUnmarshal(Object target, Object parent) {
            if (target instanceof MapReduceView) {
                MapReduceView mapReduceView = (MapReduceView) target;
                mapReduceView.setMapFunction(JsBeautify.beautify(mapReduceView.getMapFunction(), 2));
            }
            if (target instanceof SpatialView) {
                SpatialView spatialView = (SpatialView) target;
                spatialView.setMapFunction(JsBeautify.beautify(spatialView.getMapFunction(), 2));
            }
        }
    }
}
