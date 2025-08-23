package org.example.gfgblr9.models;

import lombok.Data;
import lombok.NonNull;

@Data
public class Record {
    @NonNull
    public String name;
    @NonNull
    public String email;
    @NonNull
    public String phone;
}
