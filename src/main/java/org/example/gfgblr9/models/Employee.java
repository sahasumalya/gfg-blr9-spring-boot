package org.example.gfgblr9.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@AllArgsConstructor
public class Employee {
    private int id;

    @Setter
    private String name;
}
