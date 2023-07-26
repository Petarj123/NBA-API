package com.api.nba.DTO;

public record ExceptionResponse(Integer status, String error, String message) {
}
