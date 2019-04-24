package com.test.bank.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

public class YamlUtils {

    public static final ObjectMapper ymlMapper = new ObjectMapper(new YAMLFactory());

    public static String toYaml(Object object) {
        try {
            return ymlMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
