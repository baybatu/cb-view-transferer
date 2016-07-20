package com.batuhanbayrakci.cbviewtransferer;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonSerializer {

    private ObjectMapper objectMapper = new ObjectMapper();

    public JsonSerializer() {
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                .configure(JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true)
                .setVisibility(objectMapper.getSerializationConfig()
                        .getDefaultVisibilityChecker()
                        .withFieldVisibility(JsonAutoDetect.Visibility.ANY)
                        .withGetterVisibility(JsonAutoDetect.Visibility.NONE)
                        .withSetterVisibility(JsonAutoDetect.Visibility.NONE)
                        .withCreatorVisibility(JsonAutoDetect.Visibility.NONE));

        objectMapper.getSerializationConfig().withSerializationInclusion(JsonInclude.Include.NON_NULL);
    }

    public <T> T fromJson(String jsonString, Class<T> clazz) {
        try {
            return objectMapper.readValue(jsonString, clazz);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}