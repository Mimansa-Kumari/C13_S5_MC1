package com.example.Mc1.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code= HttpStatus.NOT_FOUND, reason = "Track with this id is not found")
public class TrackNotFoundException extends Exception{
}
