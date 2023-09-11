package com.example.astontest.exceptions;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ErrorHandleUtils {
    private ErrorHandleUtils(){}
    private static final ObjectMapper objectMapper = new ObjectMapper();
    public static JsonNode buildErrorMessage(String message){
        return objectMapper.createObjectNode().put("error", message);
    }
}
