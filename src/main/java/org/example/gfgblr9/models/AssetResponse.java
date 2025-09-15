package org.example.gfgblr9.models;

import jakarta.persistence.Column;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;
import org.example.gfgblr9.enums.LibraryAssetType;

import java.sql.Timestamp;

@Data
public class AssetResponse {
    private String name;
    private String description;
    private String author;
    private Timestamp publishedAt;
    private LibraryAssetType type;
}
