package org.example.gfgblr9.models;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name="student_address1")
public class StudentAddress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;



    private String street;
    private String country;
    private String state;
    private String zipcode;


    @ManyToOne
    @JoinColumn(name="student_id", referencedColumnName = "id")
    private Student student;


}
