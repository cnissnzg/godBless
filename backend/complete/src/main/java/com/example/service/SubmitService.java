package com.example.service;

import com.example.entity.*;
import com.example.mapper.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

import java.util.*;

@Service
public class SubmitService {
  @Autowired
  private SubmitMapper submitMapper;
  public int insertSubmit(SubmitItem submitItem){
    return submitMapper.insertSubmit(submitItem);
  }
  public List<SubmitItem> getStudentSubmit(String username){
    return submitMapper.getStudentSubmit(username);
  }
}
