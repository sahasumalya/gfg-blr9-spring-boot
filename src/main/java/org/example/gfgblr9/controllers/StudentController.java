package org.example.gfgblr9.controllers;

import org.example.gfgblr9.models.Student;
import org.example.gfgblr9.models.StudentInfo;
import org.example.gfgblr9.services.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class StudentController {

    private StudentService studentService;

    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }
    @PostMapping("/createStudent") public Student createStudent(@RequestBody StudentInfo studentInfo) {

       return studentService.createStudent(studentInfo);

    }

    @GetMapping("/student")
    public Student getStudents(@RequestParam(name="id") Long studentId) {
        return studentService.findStudentById(studentId);

    }

    @GetMapping("/students")
    public List<Student> getStudents(@RequestParam(name="name") String firstName) {
        return studentService.findAllStudentsByFirstName(firstName);
    }
}
