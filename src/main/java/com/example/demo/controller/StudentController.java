package com.example.demo.controller;

import com.example.demo.model.Student;
import com.example.demo.service.StudentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("api/1.0/students")
public class StudentController {
    private  final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping
    public List<Student> getAllStudents(){
        return studentService.getAllStudents();
    }

    @GetMapping("/{id}")
    public Student getStudentById(@PathVariable Long id){
        return studentService.getStudentById(id);
    }

    @PostMapping
    public ResponseEntity<?> createStudent(@RequestBody Student student){
        try {
            Student newStudent = studentService.createStudent(student);
            return new ResponseEntity<>(newStudent, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }

    }



}
