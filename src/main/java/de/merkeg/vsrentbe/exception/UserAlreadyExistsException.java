package de.merkeg.vsrentbe.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Email is already used on existing user")
public class UserAlreadyExistsException extends RuntimeException{

}
