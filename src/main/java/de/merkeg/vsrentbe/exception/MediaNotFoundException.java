package de.merkeg.vsrentbe.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Media not found")
public class MediaNotFoundException extends RuntimeException{

}
