package com.watermark.service;

import com.watermark.entity.*;
import com.watermark.mapper.*;
import org.apache.ibatis.annotations.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

import java.util.*;

@Service
public class AlgorithmService {
  @Autowired
  private AlgorithmMapper algorithmMapper;
  public int addAlgorithm(Algorithm algorithm){
    return algorithmMapper.addAlgorithm(algorithm);
  }
  public CommonList getAlgorithmList(int start, int end){
    return new CommonList<Algorithm>(algorithmMapper.getAlgorithmList(start,end),algorithmMapper.getTot());
  }
  public CommonList getAlgorithmListByUid(int start,int end,String uid){
    return new CommonList<Algorithm>(algorithmMapper.getAlgorithmListByUid(start,end,uid),algorithmMapper.getUserTot(uid));
  }
  public Algorithm getAlgorithmById(int algorithmId){
    return algorithmMapper.getAlgorithmById(algorithmId);
  }
  public int delAlgorithm(String algorithmId){
    return algorithmMapper.delAlgorithm(algorithmId);
  }
  public int check(String uid,String name){
    return algorithmMapper.check(uid,name);
  }
}
