package com.gamestats.gamestats.responsehandler;

import java.util.Map;
import java.util.LinkedHashMap;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResponseHandler {
    public static ResponseEntity<Object> generateResponse(HttpStatus status, String message, Object responseBody) {

        Map<String, Object> map = new LinkedHashMap<>();
        map.put("status", status.value());
        map.put("message", message);

        if (responseBody != null) {
            map.put("data", responseBody);
        }

        return new ResponseEntity<Object>(map, status);
    }
}
