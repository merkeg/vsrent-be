package de.merkeg.vsrentbe.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@ResponseStatus(value = HttpStatus.FORBIDDEN, reason = "No permission to delete media")
public class MediaDeletionNoPermissionException extends RuntimeException{

}
