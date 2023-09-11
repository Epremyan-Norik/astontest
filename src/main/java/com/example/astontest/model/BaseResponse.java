package com.example.astontest.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Builder
@Getter
public class BaseResponse {

    private HttpStatus status;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private JsonNode data;
}
