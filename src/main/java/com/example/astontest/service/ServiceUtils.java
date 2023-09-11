package com.example.astontest.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class ServiceUtils {
    private ServiceUtils(){}
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static JsonNode handleError(Exception e){
        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("error", e.getMessage());
        return objectNode;
    }

    public static JsonNode createErrorNode(String errorMessage){
        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("error", errorMessage);
        return objectNode;
    }

}
