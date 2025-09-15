package org.example.gfgblr9.repositories;

import org.example.gfgblr9.models.LibraryUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;


public interface LibraryUserRepository extends CrudRepository<LibraryUser, Long> {

    @Query(value = "select * from library_users where username=?1 and password=?2",nativeQuery = true)
    public LibraryUser findByUsernameAndPassword(String username, String password);

    @Query(value = "select * from library_users where username=?1",nativeQuery = true)
    public LibraryUser findByUsername(String username);
}
