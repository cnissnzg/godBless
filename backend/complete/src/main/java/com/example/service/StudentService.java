package com.example.service;

import com.example.entity.*;
import com.example.mapper.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

import java.util.*;

@Service
public class StudentService {
  @Autowired
  private StudentMapper studentMapper;
  public Student getOneStudent(String username){
    return studentMapper.getOneStudent(username);
  }
  public List<Student> getAllStudent(){
    return studentMapper.getAllStudent();
  }
  public Student getStudentByMajor(String major){
    return studentMapper.getStudentByMajor(major);
  }
  public Student getStudentByAcademy(String academy){
    return studentMapper.getStudentByAcademy(academy);
  }
  public Student getStudentByGrade(String grade){
    return studentMapper.getStudentByGrade(grade);
  }
  public Student getStudentByUsername(String username){
    return studentMapper.getStudentByUsername(username);
  }
  public int insertStudent(Student student){
    return studentMapper.insertStudent(student);
  }
  public int updateStudent(String username,Student student){
    return studentMapper.updateStudent(username, student);
  }
  public int deleteStudent(String username){
    return studentMapper.deleteStudent(username);
  }

}
