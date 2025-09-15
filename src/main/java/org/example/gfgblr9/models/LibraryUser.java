package org.example.gfgblr9.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import org.example.gfgblr9.entity_listeners.UserEntityListener;
import org.example.gfgblr9.enums.LibraryUserRoles;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "library_users")
//@EntityListeners(UserEntityListener.class)
public class LibraryUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;
    @Transient
    private String nickname;
    @Enumerated(EnumType.ORDINAL)
    private LibraryUserRoles role;
    @Temporal(TemporalType.TIMESTAMP)
    private Timestamp created;

    @JsonIgnore
    @OneToMany(mappedBy = "contributor", cascade = CascadeType.ALL, orphanRemoval = false)
    private List<LibraryAsset> contributions = new ArrayList<>();






}
