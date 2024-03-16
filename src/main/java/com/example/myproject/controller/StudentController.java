package com.example.myproject.controller;

import com.example.myproject.model.Student;
import com.example.myproject.service.IStudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("student")
@CrossOrigin("*")
public class StudentController {
    @Autowired
    private IStudentService studentService;

    @GetMapping("")
    public ResponseEntity<?> getAllStudent() {
        List<Student> students = this.studentService.findAll();

        return new ResponseEntity<>(students, HttpStatus.OK);
    }
}
