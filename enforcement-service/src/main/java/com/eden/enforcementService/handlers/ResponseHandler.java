package com.eden.enforcementService.handlers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class ResponseHandler {
    public static ResponseEntity<Object> generateResponse(String message, HttpStatus status, Object responseObj) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("message", message);
        map.put("status", status.value());
        if (message.toLowerCase(Locale.ROOT).contains("error")) {
            map.put("errors", responseObj);

        } else {
            map.put("data", responseObj);
        }

        return new ResponseEntity<Object>(map, status);
    }
}