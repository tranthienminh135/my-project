package com.example.myproject.service;

import com.example.myproject.dto.RequestDto;
import com.example.myproject.dto.SCDto;
import com.example.myproject.model.StudentCourses;
import org.springframework.data.domain.Page;

public interface ISCService {
    Page<StudentCourses> findAllPage(RequestDto requestDto);

    void delete(Long id);

    void createSC(SCDto scDto);
}
