package com.example.Mc1.Exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT, reason = "Track with this id already exists")
public class TrackAlreadyExistException extends Exception{
}
