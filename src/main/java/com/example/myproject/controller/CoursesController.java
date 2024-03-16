package com.example.myproject.controller;


import com.example.myproject.model.Courses;
import com.example.myproject.service.ICoursesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("courses")
@CrossOrigin("*")
public class CoursesController {

    @Autowired
    private ICoursesService coursesService;

    @GetMapping("")
    public ResponseEntity<?> getAllCourses() {
        List<Courses> courses = this.coursesService.findAll();
        return new ResponseEntity<>(courses, HttpStatus.OK);
    }

}
