package org.example.gfgblr9.controllers;

import org.example.gfgblr9.models.Record;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

@RestController("/v1")
public class HealthController {

    @RequestMapping(name="get",path = "/healthCheck")
    public String checkHealth() {
        return "OK";
    }

    @GetMapping("/welcome/{version}")
    public String welcome(@RequestParam(value = "q") String name, @PathVariable("version") String v) {
        return "Welcome " + name + " to " + v;
    }

    @PostMapping("/welcome/kyc")
    public ResponseEntity<Record> welcomeKyc(@RequestBody org.example.gfgblr9.models.Record record) {
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add("Content-Type", "application/json");
        headers.add("Accept", "application/json");
        headers.add("tid", "sdsdcdvcd");
        return new ResponseEntity<>(record, headers, HttpStatus.ACCEPTED);

    }
}
