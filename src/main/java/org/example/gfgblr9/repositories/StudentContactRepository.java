package org.example.gfgblr9.repositories;

import org.example.gfgblr9.models.StudentContact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentContactRepository extends CrudRepository<StudentContact, Long> {

    @Query(value = "select * from student_contacts as s where s.id = ?1", nativeQuery = true)
    public StudentContact searchByContactId(Long id);

}
