package com.comeongroup.assignment.exception;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class ExceptionResponse {
    private LocalDateTime dateTime;
    private String message;
}