package com.batuhanbayrakci.cbviewtransferer;

import com.batuhanbayrakci.cbviewtransferer.model.Buckets;
import com.sun.xml.internal.bind.marshaller.CharacterEscapeHandler;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.File;
import java.io.IOException;
import java.net.URI;

public class ViewXMLFileCreator implements ViewCreator {

    public void create(Buckets buckets, URI targetURI) throws IOException {
        File file = new File(targetURI.toString());
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(Buckets.class);

            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

            jaxbMarshaller.setProperty("com.sun.xml.internal.bind.characterEscapeHandler",
                    (CharacterEscapeHandler) (ac, i, j, flag, writer) -> writer.write(ac, i, j));

            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            jaxbMarshaller.marshal(buckets, file);
            jaxbMarshaller.marshal(buckets, System.out);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

}
