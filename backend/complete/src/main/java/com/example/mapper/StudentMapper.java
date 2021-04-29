package com.example.mapper;

import com.example.entity.*;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.*;

import java.util.*;

@Repository
public interface StudentMapper {
  Student getOneStudent(String username);
  List<Student> getAllStudent();
  Student getStudentByMajor(String major);
  Student getStudentByAcademy(String academy);
  Student getStudentByGrade(String grade);
  Student getStudentByUsername(String username);
  int insertStudent(Student student);
  int updateStudent(@Param("username") String username,@Param("student") Student student);
  int deleteStudent(String username);
}
