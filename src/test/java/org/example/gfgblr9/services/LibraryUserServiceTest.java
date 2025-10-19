package org.example.gfgblr9.services;

import org.example.gfgblr9.enums.LibraryUserRoles;
import org.example.gfgblr9.models.LibraryUser;
import org.example.gfgblr9.repositories.LibraryAssetRepository;
import org.example.gfgblr9.repositories.LibraryUserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataAccessResourceFailureException;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

public class LibraryUserServiceTest {


    @InjectMocks
    LibraryUserService libraryUserService;


    @Mock
    LibraryUserRepository libraryUserRepository;
    @Mock
    private LibraryAssetRepository libraryAssetRepository;


    @BeforeEach
    public void beforeEach() {
        MockitoAnnotations.initMocks(this);
    }



    @Test
    public void demoTest(){
        System.out.println("demoTest");
    }

    @Test
    public void registerTestWithExistingUser(){
         LibraryUser libraryUser = new LibraryUser();
         libraryUser.setUsername("test");
         libraryUser.setPassword("qwerty");
         when(libraryUserRepository.findByUsername("test_username")).thenReturn(libraryUser);

         Assertions.assertNull(libraryUserService.register("test_username", "scvdccsc", LibraryUserRoles.CUSTOMER));
    }

    @Test
    public void registerTestWithNewUser(){
        when(libraryUserRepository.findByUsername("test_username")).thenReturn(null);
        when(libraryUserRepository.save(any(LibraryUser.class))).thenReturn(new LibraryUser());
        LibraryUser libraryUser = libraryUserService.register("test_username", "scvdccsc", LibraryUserRoles.CUSTOMER);
        Assertions.assertNotNull(libraryUser);
        Assertions.assertEquals("test_username", libraryUser.getUsername());
        Assertions.assertEquals("scvdccsc", libraryUser.getPassword());
    }


    @Test
    public void registerTestWithDatabaseDown(){
        when(libraryUserRepository.findByUsername("test_username")).thenThrow(new DataAccessResourceFailureException("test_database_error"));
        //doRe(new RuntimeException("ycy")).when(libraryUserRepository).save(any(LibraryUser.class));
        RuntimeException exception = assertThrows(RuntimeException.class, ()-> libraryUserService.register("test_username", "scvdccsc", LibraryUserRoles.CUSTOMER));
        Assertions.assertEquals("database_error", exception.getMessage());
    }

    /**
     *          assertThrows(
     *                       RuntimeException.class, () -> libraryUserService.register("test_username", "scvdccsc", LibraryUserRoles.CUSTOMER));
     *          )
     */

}
