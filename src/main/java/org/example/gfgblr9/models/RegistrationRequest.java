package org.example.gfgblr9.models;

import lombok.Data;
import lombok.NonNull;
import org.example.gfgblr9.enums.LibraryUserRoles;

@Data
public class RegistrationRequest {
    @NonNull
    private String username;
    @NonNull
    private String password;


    private LibraryUserRoles role;
}
