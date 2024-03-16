package com.example.myproject.repository;

import com.example.myproject.model.Courses;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ICoursesRepository extends JpaRepository<Courses, Long> {
}
