package com.example.controller;

import com.example.entity.*;
import com.example.service.*;
import com.sun.org.apache.xerces.internal.xs.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("api/v1/relations")
public class RelationController {
  @Autowired
  private RelationService relationService;

  @RequestMapping("addOne")
  public int addRelation(@RequestParam("student")String student,@RequestParam("teacher")String teacher){
    return relationService.addRelation(student, teacher);
  }

  @RequestMapping("delOne")
  public int delRelation(@RequestParam("student") String student,@RequestParam("teacher") String teacher){
    return relationService.delRelation(student, teacher);
  }
  @RequestMapping("getTeacherToStudent")
  public List<String> getAllTeacher(String student){
    List<Relation> relationList = relationService.getAllTeacher(student);
    List<String> stringList = new ArrayList<>();
    for(Relation relation:relationList){
      stringList.add(relation.getTeacher());
    }
    return stringList;
  }
  @RequestMapping("getStudentToTeacher")
  public List<String> getAllStudent(String teacher){
    List<Relation> relationList = relationService.getAllStudent(teacher);
    List<String> stringList = new ArrayList<>();
    for(Relation relation:relationList){
      stringList.add(relation.getStudent());
    }
    return stringList;
  }

}
