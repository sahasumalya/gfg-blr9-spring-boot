package org.example.gfgblr9.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.example.gfgblr9.annotations.InitSalary;
import org.example.gfgblr9.annotations.JsonSerializableField;

import java.util.ArrayList;

@Data
@AllArgsConstructor
public class Employee {
    private int id;

    @JsonSerializableField("Mr")
    private String firstName;

    @JsonSerializableField
    private String lastName;

    @JsonSerializableField("age")
    private int age;

    private int salary;

    public String getEmployeeDetails(String title) {
        return title + " " + firstName+ " "+ lastName;
    }

    @InitSalary(10000)
    public void initialiseSalary(int salary) {
        this.salary = salary;
    }

    public Employee constructEmployee(int id, String firstName, String lastName, int age, int salary) {
        return new Employee(id, firstName, lastName, age, salary);
    }
}
