package org.example.gfgblr9.models;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name="student_contacts")
public class StudentContact {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String phone;
    private String email;
    @OneToOne(mappedBy = "contact", cascade = CascadeType.ALL)
    private Student student;

}
