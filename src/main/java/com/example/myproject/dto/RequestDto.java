package com.example.myproject.dto;

import lombok.Data;
import org.springframework.data.domain.Sort;

import java.sql.Date;

@Data
public class RequestDto {

    private Integer page;

    private Integer size;

    private Sort.Direction sortDirection;

    private String sortBy;

    private Long coursesId;

    private Long studentId;

    private String description;

    private Date startDate;

    private Date endDate;
}
