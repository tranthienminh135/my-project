package com.example.myproject.service.impl;

import com.example.myproject.model.Student;
import com.example.myproject.repository.IStudentRepository;
import com.example.myproject.service.IStudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService implements IStudentService {

    @Autowired
    private IStudentRepository studentRepository;

    @Override
    public List<Student> findAll() {
        return this.studentRepository.findAll();
    }
}
