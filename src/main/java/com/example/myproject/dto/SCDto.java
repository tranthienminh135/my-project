package com.example.myproject.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


@Data
public class SCDto implements Validator {

    private Long id;

    private Date startDate;

    @NotBlank(message = "Not blank")
    private String description;

    private Long studentId;

    private Long coursesId;

    @Override
    public boolean supports(Class<?> clazz) {
        return false;
    }

    @Override
    public void validate(Object target, Errors errors) {
        SCDto scDto = (SCDto) target;
        String pattern = "yyyy-MM-dd";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        try {
            LocalDate.parse(scDto.getStartDate().toString(), formatter);
        } catch (Exception e) {
            errors.rejectValue("startDate", "", "age");
        }
    }
}
