package com.example.myproject.repository;

import com.example.myproject.dto.RequestDto;
import com.example.myproject.model.StudentCourses;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface ISCRepository extends JpaRepository<StudentCourses,Long> {

    @Query(value = " select sc from StudentCourses sc " +
            " join Courses c on c.id = sc.courses.id " +
            " join Student s on s.id = sc.student.id " +
            " where sc.description like concat('%', :#{#requestDto.description} , '%') " +
            " and (sc.student.id = :#{#requestDto.studentId} or :#{#requestDto.studentId} = -1) " +
            " and (sc.courses.id = :#{#requestDto.coursesId} or :#{#requestDto.coursesId} = -1) " +
            " and ((sc.startDate between :#{#requestDto.startDate} and :#{#requestDto.endDate}) or :#{#requestDto.startDate} is null or :#{#requestDto.endDate} is null )" +
            " and sc.isDelete = false ",
    countQuery = " select count(sc.id) from StudentCourses sc " +
            " join Courses c on c.id = sc.student.id " +
            " join Student s on s.id = sc.courses.id " +
            " where sc.description like concat('%', :#{#requestDto.description} , '%') " +
            " and (sc.student.id = :#{#requestDto.studentId} or :#{#requestDto.studentId} = -1) " +
            " and (sc.courses.id = :#{#requestDto.coursesId} or :#{#requestDto.coursesId} = -1) " +
            " and ((sc.startDate between :#{#requestDto.startDate} and :#{#requestDto.endDate}) or :#{#requestDto.startDate} is null or :#{#requestDto.endDate} is null )" +
            " and sc.isDelete = false ")
    Page<StudentCourses> findAllPage(@Param("requestDto") RequestDto requestDto, Pageable pageable);

    @Modifying
    @Transactional
    @Query(value = " update StudentCourses sc set sc.isDelete = true where sc.id = :id ")
    void deleteSC(@Param("id") Long id);
}
