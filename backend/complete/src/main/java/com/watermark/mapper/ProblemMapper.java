package com.watermark.mapper;

import com.watermark.entity.*;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.*;

import java.util.*;

@Repository
public interface ProblemMapper {
  public List<Problem> getProblemList(@Param("start")int start,@Param("end")int end);
  public Problem getProblem(@Param("pid") int pid);
  public int getTot();
}
