package de.merkeg.vsrentbe.exception;


import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "No refresh token set in authorization header")
public class NoRefreshTokenSetException extends RuntimeException{

}
