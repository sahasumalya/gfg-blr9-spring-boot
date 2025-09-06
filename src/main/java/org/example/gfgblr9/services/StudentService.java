package org.example.gfgblr9.services;

import org.example.gfgblr9.models.Student;
import org.example.gfgblr9.models.StudentInfo;
import org.example.gfgblr9.repositories.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {

    private final StudentRepository repository;
    private final StudentRepository studentRepository;


    @Autowired
    public StudentService(StudentRepository repository, StudentRepository studentRepository) {
        this.repository = repository;
        this.studentRepository = studentRepository;
    }

    public Student createStudent(StudentInfo studentInfo) {
        Student student = new Student();
        student.setFirstName(studentInfo.getFirstName());
        student.setLastName(studentInfo.getLastName());
        return repository.save(student);

    }

    public Student findStudentById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public List<Student> findAllStudentsByFirstName(String firstName) {
        return studentRepository.findByFirstName(firstName);
    }
}
