package com.example.mapper;

import com.example.entity.*;
import org.springframework.stereotype.*;

import java.util.*;

@Repository
public interface CourseMapper {
  public int addCourse(Course course);
  public List<Course> getAllCourse();
  public int getId(String title);
}
