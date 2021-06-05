package com.watermark.mapper;

import com.watermark.entity.*;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.*;

import java.util.*;

@Repository
public interface AlgorithmMapper {
  public int addAlgorithm(Algorithm algorithm);
  public List<Algorithm> getAlgorithmList(@Param("start")int start,@Param("end")int end);
  public List<Algorithm> getAlgorithmListByUid(@Param("start")int start,@Param("end")int end,@Param("uid")String uid);
  public Algorithm getAlgorithmById(@Param("algorithmId")int algorithmId);
  public int delAlgorithm(@Param("algorithmId") String algorithmId);
  public int check(@Param("uid") String uid,@Param("name")String name);
  public int getTot();
  public int getUserTot(@Param("uid")String uid);
}
