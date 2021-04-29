package com.example.controller;

import com.example.entity.*;
import com.example.service.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("api/v1/course")
public class CourseController {
  @Autowired
  private CourseService courseService;
  @RequestMapping("add")
  public int addCourse(@RequestBody Course course){
    return courseService.addCourse(course);
  }
  @RequestMapping("getAll")
  public List<Course> getAllCourse(){
    return courseService.getAllCourse();
  }

}
