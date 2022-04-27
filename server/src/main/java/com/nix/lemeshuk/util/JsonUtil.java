package com.nix.lemeshuk.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nix.lemeshuk.exception.DataProcessingException;
import com.nix.lemeshuk.model.DefaultRolePage;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Component
public class JsonUtil {

    public final String READ_LIST_FROM_JSON_ERROR = "Failed to get list from JSON file with name %s";

    public List<DefaultRolePage> readListFromJsonFile(String fileName) {
        ObjectMapper mapper = new ObjectMapper();
        InputStream fileInputStream = JsonUtil.class.getClassLoader().getResourceAsStream(fileName);

        try {
            return mapper.readValue(fileInputStream, new TypeReference<>() {
            });
        } catch (IOException e) {
            throw new DataProcessingException(String.format(READ_LIST_FROM_JSON_ERROR, fileName), e);
        }
    }
}