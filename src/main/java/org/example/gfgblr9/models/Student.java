package org.example.gfgblr9.models;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.id.factory.spi.GenerationTypeStrategy;

import java.util.List;

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
    // fetch, cascade
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "contact_id", referencedColumnName = "id")
    private StudentContact contact;

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL)
    private List<StudentAddress> addresses;

    /*@ManyToMany
    @JoinTable(
            name="course_students",
            joinColumns =
                    @JoinColumn(name = "id", referencedColumnName = "student_id"),
            inverseJoinColumns = @JoinColumn(name = "id", referencedColumnName = "course_id")
    )*/
    //private List<Course> likedCourses;
}
