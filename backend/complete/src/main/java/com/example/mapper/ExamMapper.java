package com.example.mapper;

import com.example.entity.*;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.*;

import java.util.*;

@Repository
public interface ExamMapper {
  public int addExam(Exam exam);
  public int delExam(int eid);
  public int updateExam(@Param("eid") int eid,@Param("exam") Exam exam);
  public Exam getOneExam(int eid);
  public List<Exam> getAllExam();

}
