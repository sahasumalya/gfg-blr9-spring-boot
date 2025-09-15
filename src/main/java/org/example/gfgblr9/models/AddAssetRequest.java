package org.example.gfgblr9.models;

import lombok.Data;
import org.example.gfgblr9.enums.LibraryAssetType;

@Data
public class AddAssetRequest {
    private String name;
    private String description;
    private String author;
    private LibraryAssetType type;
    private String username;
}
