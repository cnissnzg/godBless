package com.example.mapper;

import com.example.entity.*;
import org.springframework.stereotype.*;

import java.util.*;

@Repository
public interface SubmitMapper {
  //public List<SubmitItem> getClassSubmit(String username);
  public int insertSubmit(SubmitItem submitItem);
  public List<SubmitItem> getStudentSubmit(String username);
}
