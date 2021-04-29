package com.example.service;

import com.example.entity.*;
import com.example.mapper.*;
import com.example.util.*;
import org.apache.ibatis.annotations.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

import java.util.*;

@Service
public class ExamService {
  @Autowired
  private ExamMapper examMapper;
  public int addExam(Exam exam){
    return examMapper.addExam(exam);
  }
  public int delExam(int eid){
    return examMapper.delExam(eid);
  }
  public int updateExam(int eid,Exam exam){
    return examMapper.updateExam(eid,exam);
  }
  public Exam getOneExam(int eid){
    return examMapper.getOneExam(eid);
  }
  public List<Exam> getAllExam(){
    return examMapper.getAllExam();
  }
}
