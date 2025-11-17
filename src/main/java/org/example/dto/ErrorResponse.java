package org.example.dto;

import lombok.Builder;
import lombok.Value;

import java.util.List;

@Value
@Builder
public class ErrorResponse {
    String message;
    Integer status;
    List<String> errors;
}