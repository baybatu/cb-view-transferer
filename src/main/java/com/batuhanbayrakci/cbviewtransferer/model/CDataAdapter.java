package com.batuhanbayrakci.cbviewtransferer.model;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class CDataAdapter extends XmlAdapter<String, String> {

    @Override
    public String marshal(String value) throws Exception {
        return "<![CDATA[" + value + "]]>";
    }

    @Override
    public String unmarshal(String value) throws Exception {
        return value;
    }

}
