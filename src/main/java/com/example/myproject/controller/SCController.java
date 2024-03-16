package com.example.myproject.controller;

import com.example.myproject.dto.RequestDto;
import com.example.myproject.dto.SCDto;
import com.example.myproject.model.StudentCourses;
import com.example.myproject.service.ISCService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("sc")
@CrossOrigin("*")
public class SCController {

    @Autowired
    private ISCService scService;

    @PostMapping("")
    public ResponseEntity<?> showList(@RequestBody RequestDto requestDto) {
        Page<StudentCourses> studentCourses = this.scService.findAllPage(requestDto);
        return new ResponseEntity<>(studentCourses, HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteSC(@PathVariable Long id) {
        this.scService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<?> createSC(@Valid @RequestBody SCDto scDto, BindingResult bindingResult) {
        SCDto sc = new SCDto();
        sc.validate(scDto, bindingResult);
        if (bindingResult.hasErrors()) {
           return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        this.scService.createSC(scDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
