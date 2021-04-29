package com.example.service;

import com.example.entity.*;
import com.example.mapper.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.core.io.*;
import org.springframework.core.io.support.*;
import org.springframework.http.*;
import org.springframework.stereotype.*;

import java.io.*;

@Service
public class TeacherService {
  @Autowired
  private TeacherMapper teacherMapper;
  public int insertTeacher(Teacher teacher){
    return teacherMapper.insertTeacher(teacher);
  }
  public int deleteTeacher(String username){
    return teacherMapper.deleteTeacher(username);
  }
  public Teacher getOneTeacher(String username){
    return teacherMapper.getOneTeacher(username);
  }
  public int updateTeacher(String username,Teacher teacher){
    return teacherMapper.updateTeacher(username, teacher);
  }


}
