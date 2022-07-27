package com.example.studentApp;


import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping("/students")
public class StudentController {

    private final StudentRepository studentRepository;

    public StudentController(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }


    @GetMapping
    public List<Student> getStudents() {
        return studentRepository.findAll();
    }


    @GetMapping("/{id}")
    public Student getStudent(@PathVariable Long id) {
        return studentRepository.findById(id).orElseThrow(RuntimeException::new);
    }

    @PostMapping
    public ResponseEntity createStudent(@RequestBody Student student)
            throws URISyntaxException {
        Student newStudent = studentRepository.save(student);
        return ResponseEntity.created(new URI("/students/" + newStudent.getId())).body(newStudent);
    }

    @PutMapping("/{id}")
    public ResponseEntity updateStudent(@PathVariable Long id, @RequestBody Student student) {
        Student currentStudent =
                studentRepository.findById(id).orElseThrow(RuntimeException::new);
        currentStudent.setFirstName(student.getFirstName());
        currentStudent.setLastName(student.getLastName());
        currentStudent.setCourse(student.getCourse());
        currentStudent = studentRepository.save(student);

        return ResponseEntity.ok(currentStudent);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteStudent(@PathVariable Long id) {
        studentRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }

}
