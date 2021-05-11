package com.watermark.service;

import com.watermark.entity.*;
import com.watermark.mapper.*;
import org.apache.ibatis.annotations.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

import java.util.*;

@Service
public class ProblemService {
  @Autowired
  public ProblemMapper problemMapper;
  @Autowired
  public TagMapper tagMapper;
  public ProblemList getProblemList(int start,int end){
    List<Problem> problemList = problemMapper.getProblemList(start, end);
    for(Problem problem:problemList){
      problem.setTags(tagMapper.getProblemTags(problem.getPid()));
    }
    return new ProblemList(problemList,problemMapper.getTot());
  }
  public Problem getProblem(@Param("pid") int pid){
    Problem problem = problemMapper.getProblem(pid);
    problem.setTags(tagMapper.getProblemTags(problem.getPid()));
    return problem;
  }
}
