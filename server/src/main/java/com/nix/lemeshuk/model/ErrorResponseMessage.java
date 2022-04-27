package com.nix.lemeshuk.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import javax.ws.rs.core.Response;
import java.time.LocalDateTime;

@Setter
@Getter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ErrorResponseMessage {

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss")
    LocalDateTime timestamp;

    int status;
    String error;
    String message;

    public static ErrorResponseMessage of(int status, String errorType, String errorMessage) {

        return ErrorResponseMessage.builder()
                .timestamp(LocalDateTime.now())
                .status(status)
                .error(errorType)
                .message(errorMessage)
                .build();
    }

    public static ErrorResponseMessage fromStatus(Response.Status errorStatus) {

        return ErrorResponseMessage.of(
                errorStatus.getStatusCode(),
                errorStatus.name(),
                errorStatus.getReasonPhrase());
    }
}
