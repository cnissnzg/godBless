package com.example.service;

import com.example.entity.*;
import com.example.mapper.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

import java.util.*;

@Service
public class CourseService {
  @Autowired
  public CourseMapper courseMapper;
  public int addCourse(Course course){
    return courseMapper.addCourse(course);
  }
  public List<Course> getAllCourse(){
    return courseMapper.getAllCourse();
  }
  public int getId(String title){return courseMapper.getId(title);}
}
