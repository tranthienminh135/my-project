package com.example.myproject.model;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Date;

@Entity
@Data
public class StudentCourses {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Date startDate;

    private String description;

    @Column(columnDefinition = "bit(1) default 0")
    private Boolean isDelete;

    @ManyToOne
    @JoinColumn(name = "student_id", referencedColumnName = "id")
    private Student student;

    @ManyToOne
    @JoinColumn(name = "courses_id", referencedColumnName = "id")
    private Courses courses;
}
