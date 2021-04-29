package com.watermark.service;

import com.watermark.entity.*;
import com.watermark.mapper.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

import java.util.*;

@Service
public class ProblemService {
  @Autowired
  public ProblemMapper problemMapper;
  public ProblemList getProblemList(int start,int end){
    return new ProblemList(problemMapper.getProblemList(start, end),problemMapper.getTot());
  }
}
