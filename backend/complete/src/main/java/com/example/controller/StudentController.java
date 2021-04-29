package com.example.controller;

import com.example.entity.*;
import com.example.service.*;
import org.apache.ibatis.annotations.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("api/v1/student")
public class StudentController {
  @Autowired
  private StudentService studentService;

  @RequestMapping("getOne")
  public Student getOneStudent(String username){
    return studentService.getOneStudent(username);
  }

  @RequestMapping("getAll")
  public List<Student> getAllStudent(){
    return studentService.getAllStudent();
  }
  @RequestMapping("addOne")
  public int insertStudent(@RequestBody Student student){
    System.out.println(student.getName());
    return studentService.insertStudent(student);
  }

  @RequestMapping("updOne")
  public int updateSudent(@RequestParam("username") String username,@RequestBody Student student){
    return studentService.updateStudent(username, student);
  }

  @RequestMapping("delOne")
  public int deleteStudent(String username){
    return studentService.deleteStudent(username);
  }
}
