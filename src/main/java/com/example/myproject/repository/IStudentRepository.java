package com.example.myproject.repository;

import com.example.myproject.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IStudentRepository extends JpaRepository<Student, Long> {
}
