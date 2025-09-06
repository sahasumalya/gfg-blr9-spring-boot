package org.example.gfgblr9.models;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.id.factory.spi.GenerationTypeStrategy;

@Data
@Entity
@Table(name="students")
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name="firstName")
    private String firstName;
    @Column(name="lastName")
    private String lastName;


}
