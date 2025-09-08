package org.example.gfgblr9.services;

import org.example.gfgblr9.models.Student;
import org.example.gfgblr9.models.StudentAddress;
import org.example.gfgblr9.models.StudentContact;
import org.example.gfgblr9.models.StudentInfo;
import org.example.gfgblr9.repositories.StudentContactRepository;
import org.example.gfgblr9.repositories.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService {

    private final StudentRepository repository;
    private final StudentRepository studentRepository;
    private final StudentContactRepository studentContactRepository;


    @Autowired
    public StudentService(StudentRepository repository, StudentRepository studentRepository, StudentContactRepository studentContactRepository) {
        this.repository = repository;
        this.studentRepository = studentRepository;
        this.studentContactRepository = studentContactRepository;
    }

    public Student createStudent(StudentInfo studentInfo) {
        Student student = new Student();
        student.setFirstName(studentInfo.getFirstName());
        student.setLastName(studentInfo.getLastName());
        StudentContact contact = new StudentContact();
        contact.setEmail("sdcscvs");
        contact.setPhone("vwvww");
        student.setContact(contact);

        StudentAddress currentAddress = new StudentAddress();
        currentAddress.setCountry("India");
        currentAddress.setState("KA");
        currentAddress.setZipcode("90210");
        currentAddress.setStreet("svesfvsf");

        StudentAddress permanentAddress = new StudentAddress();
        permanentAddress.setCountry("India");
        permanentAddress.setState("UK");
        permanentAddress.setZipcode("35435");
        permanentAddress.setStreet("ergewrfe");

        student.setAddresses(List.of(currentAddress, permanentAddress));


        return repository.save(student);

    }

    public Student findStudentById(Long id) {
        Optional<Student> student = studentRepository.findById(id);
        return repository.findById(id).orElse(null);
    }

    public List<Student> findAllStudentsByFirstName(String firstName) {
        return studentRepository.findByFirstName(firstName);
    }

    public StudentInfo findStudentOfContactById(Long contactId) {
        StudentContact studentContact = studentContactRepository.searchByContactId(contactId);
        StudentInfo studentInfo = new StudentInfo();
        studentInfo.firstName = studentContact.getStudent().getFirstName();
        studentInfo.lastName = studentContact.getStudent().getLastName();
        return studentInfo;
    }
}
