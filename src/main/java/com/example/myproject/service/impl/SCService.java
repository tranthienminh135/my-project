package com.example.myproject.service.impl;

import com.example.myproject.dto.RequestDto;
import com.example.myproject.dto.SCDto;
import com.example.myproject.model.Courses;
import com.example.myproject.model.Student;
import com.example.myproject.model.StudentCourses;
import com.example.myproject.repository.ISCRepository;
import com.example.myproject.service.ISCService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class SCService implements ISCService {

    @Autowired
    private ISCRepository iscRepository;
    @Override
    public Page<StudentCourses> findAllPage(RequestDto requestDto) {
        Pageable pageable = PageRequest.of(requestDto.getPage(), requestDto.getSize(), requestDto.getSortDirection(), requestDto.getSortBy());
        return this.iscRepository.findAllPage(requestDto, pageable);
    }

    @Override
    public void delete(Long id) {
        this.iscRepository.deleteSC(id);
    }

    @Override
    public void createSC(SCDto scDto) {
        Student student = new Student();
        student.setId(scDto.getStudentId());
        Courses courses = new Courses();
        courses.setId(scDto.getCoursesId());
        StudentCourses studentCourses = new StudentCourses();
        studentCourses.setDescription(scDto.getDescription());
        studentCourses.setStartDate(scDto.getStartDate());
        studentCourses.setStudent(student);
        studentCourses.setCourses(courses);
        studentCourses.setIsDelete(false);
        this.iscRepository.save(studentCourses);
    }
}
