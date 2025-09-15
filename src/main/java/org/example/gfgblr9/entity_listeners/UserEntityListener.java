package org.example.gfgblr9.entity_listeners;

import jakarta.persistence.PostPersist;

public class UserEntityListener {

    @PostPersist
    public void afterPersist() {
        System.out.println("library_user persisted");
    }
}
