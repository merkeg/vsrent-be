package de.merkeg.vsrentbe.exception;

import lombok.*;
import org.springframework.http.HttpStatusCode;


import java.time.LocalDateTime;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomErrorResponse {
    private LocalDateTime timestamp;
    private HttpStatusCode status;
    private String error;
    private String message;
    private Object detail;
    private String path;
}
