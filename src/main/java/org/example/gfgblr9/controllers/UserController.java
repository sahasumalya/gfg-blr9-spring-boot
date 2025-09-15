package org.example.gfgblr9.controllers;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.gfgblr9.models.*;
import org.example.gfgblr9.services.LibraryUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {

    private final LibraryUserService libraryUserService;

    private ObjectMapper objectMapper = new ObjectMapper();


    @Autowired
    public UserController(LibraryUserService libraryUserService) {
        this.libraryUserService = libraryUserService;
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest) {
        if (this.libraryUserService.login(loginRequest.getUsername(), loginRequest.getPassword())) {
            return ResponseEntity.ok("Login successful");
        }
        return ResponseEntity.badRequest().body("Invalid username or password");



    }


    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegistrationRequest registrationRequest) {
        LibraryUser libraryUser = libraryUserService.register(registrationRequest.getUsername(), registrationRequest.getPassword(), registrationRequest.getRole());
        if (libraryUser == null) {
            return ResponseEntity.badRequest().body("UserName already exists");
        }
        return ResponseEntity.ok("User registered successfully");
    }

    @PostMapping("/v1/addAsset")
    public ResponseEntity<AssetResponse> addAsset(@RequestBody AddAssetRequest addAssetRequest) {
         LibraryAsset libraryAsset = libraryUserService.addAsset(addAssetRequest);
         if (libraryAsset == null) {
             return ResponseEntity.badRequest().body(new AssetResponse());
         } else {
             AssetResponse assetResponse = new AssetResponse();
             assetResponse.setAuthor(libraryAsset.getAuthor());
             assetResponse.setDescription(libraryAsset.getDescription());
             assetResponse.setPublishedAt(libraryAsset.getPublishedAt());
             return ResponseEntity.ok(assetResponse);
         }
    }

    @GetMapping("/v1/getAsset")
    public ResponseEntity<List<AssetResponse>> getAssets(@RequestParam("username") String username) {
        return ResponseEntity.ok().body(libraryUserService.findAssets(username));
    }



}
