package com.example.demo.service.impl;

import com.example.demo.model.Student;
import com.example.demo.repo.StudentRepository;
import com.example.demo.service.StudentService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {
    private final StudentRepository studentRepository;

    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public List<Student> getAllStudents() {
        return studentRepository.getAllStudents();
    }

    @Override
    public Student getStudentById(Long id) {
        return studentRepository.findStudentById(id);
    }

    @Override
    public Student createStudent(Student student) {
        return studentRepository.insertStudent(student);
    }

    @Override
    public void deleteStudent(Long id) {
        studentRepository.deleteStudentById(id);
    }
}
