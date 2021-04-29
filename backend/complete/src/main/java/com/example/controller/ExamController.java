package com.example.controller;

import com.example.entity.*;
import com.example.service.*;
import com.example.util.*;
import org.apache.ibatis.annotations.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("api/v1/exams")
public class ExamController {
  @Autowired
  private ExamService examService;

  @RequestMapping("getOne")
  public Exam getOneExam(int eid){
    return examService.getOneExam(eid);
  }

  @RequestMapping("getAll")
  public List<Exam> getAllExam(){
    return examService.getAllExam();
  }

  @RequestMapping("addOne")
  public int addOneExam(@RequestBody Exam exam){
    return examService.addExam(exam);
  }

  @RequestMapping("updOne")
  public int updateOneExam(@RequestParam("eid") int eid,@RequestBody Exam exam){
    return examService.updateExam(eid,exam);
  }

  @RequestMapping("delOne")
  public int delOneExam(int eid){
    return examService.delExam(eid);
  }

}
