package org.example.gfgblr9.repositories;

import org.example.gfgblr9.models.Student;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends CrudRepository<Student, Long> {
    // select * from students where first_name = :firstName
    @Query(value = "SELECT s FROM Student s WHERE s.firstName = :firstName", nativeQuery = false)
    public List<Student> findByFirstName(@Param(value="firstName") String firstName);

    ///  A --> G--> B
}
