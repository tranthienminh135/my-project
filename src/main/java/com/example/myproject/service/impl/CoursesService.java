package com.example.myproject.service.impl;

import com.example.myproject.model.Courses;
import com.example.myproject.repository.ICoursesRepository;
import com.example.myproject.service.ICoursesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CoursesService implements ICoursesService {
    @Autowired
    private ICoursesRepository coursesRepository;

    @Override
    public List<Courses> findAll() {
        return this.coursesRepository.findAll();
    }
}
