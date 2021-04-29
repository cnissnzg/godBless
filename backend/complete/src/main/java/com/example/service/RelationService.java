package com.example.service;

import com.example.entity.*;
import com.example.mapper.*;
import org.apache.ibatis.annotations.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

import java.util.*;

@Service
public class RelationService {
  @Autowired
  private RelationMapper relationMapper;
  public int addRelation(String student,String teacher){
    return relationMapper.addRelation(student,teacher);
  }
  public int delRelation(String student,String teacher){
    return relationMapper.delRelation(student,teacher);
  }
  public List<Relation> getAllTeacher(String student){
    return relationMapper.getAllTeacher(student);
  }
  public List<Relation> getAllStudent(String teacher){
    return relationMapper.getAllStudent(teacher);
  }
}
