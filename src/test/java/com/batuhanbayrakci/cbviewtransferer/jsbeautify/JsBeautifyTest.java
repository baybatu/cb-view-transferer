package com.batuhanbayrakci.cbviewtransferer.jsbeautify;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class JsBeautifyTest {

    @Test
    public void shouldBeautifyJS() throws Exception {
        String uglyJSCode = "\n   function     mapFunction     ( doc  ,meta )   {\n" +
                "if    (doc.district=== 'iskenderun' ) {\nemit(doc.district , null  )\n}   " +
                "\n\n      }\n\n";
        String beautifulJSCode = "function mapFunction(doc, meta) {\n" +
                "  if (doc.district === 'iskenderun') {\n" +
                "    emit(doc.district, null)\n" +
                "  }\n" +
                "\n" +
                "}";
        assertThat(JsBeautify.beautify(uglyJSCode, 2), equalTo(beautifulJSCode));
    }
}
