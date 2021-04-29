package com.example.controller;

import com.example.entity.*;
import com.example.service.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.core.io.*;
import org.springframework.core.io.support.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.io.*;

@RestController
@RequestMapping("api/v1/teacher")
public class TeacherController {
  @Autowired
  private TeacherService teacherService;
  @RequestMapping("addOne")
  public int insertTeacher(@RequestBody Teacher teacher){
    return teacherService.insertTeacher(teacher);
  }
  @RequestMapping("delOne")
  public int deleteTeacher(String username){
    return teacherService.deleteTeacher(username);
  }
  @RequestMapping("getOne")
  public Teacher getOneTeacher(String username){
    return teacherService.getOneTeacher(username);
  }
  @RequestMapping("updOne")
  public int updateTeacher(@RequestParam("username") String username,@RequestBody Teacher teacher){
    return teacherService.updateTeacher(username, teacher);
  }

}
