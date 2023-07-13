package de.merkeg.vsrentbe.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "User does not exist")
public class UserDoesNotExistException extends RuntimeException{

}
