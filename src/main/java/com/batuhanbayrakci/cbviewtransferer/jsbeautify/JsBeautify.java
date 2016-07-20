package com.batuhanbayrakci.cbviewtransferer.jsbeautify;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

/**
 * Taken from https://github.com/ioikka/JsBeautifier
 * */
public class JsBeautify {

    private static final String BEAUTIFY_JS = "/jsbeautifier.js";

    public static String beautify(String jsCode) {
        return beautify(jsCode, 2);
    }

    public static String beautify(String jsCode, int indentSize) {
        Context cx = Context.enter();
        Scriptable scope = cx.initStandardObjects();
        InputStream resourceAsStream = JsBeautify.class.getResourceAsStream(BEAUTIFY_JS);

        try {
            Reader reader = new InputStreamReader(resourceAsStream);
            cx.evaluateReader(scope, reader, "__beautify.js", 1, null);
            reader.close();
        } catch (IOException e) {
            throw new Error("Error reading " + "beautify.js");
        }
        scope.put("jsCode", scope, jsCode);
        return (String) cx.evaluateString(scope, "js_beautify(jsCode, {indent_size:" + indentSize + "})",
                "inline", 1, null);
    }

}