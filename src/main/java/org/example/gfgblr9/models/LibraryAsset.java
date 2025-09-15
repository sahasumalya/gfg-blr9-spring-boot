package org.example.gfgblr9.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import org.example.gfgblr9.enums.LibraryAssetType;

import java.sql.Timestamp;


@Data
@Entity
@Table(name = "library_assets")
public class LibraryAsset {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;
    private String author;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="published_at")
    private Timestamp publishedAt;

    @Enumerated(EnumType.STRING)
    private LibraryAssetType type;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "contributor_id", referencedColumnName = "id")
    @JsonIgnore
    private LibraryUser contributor;


}
