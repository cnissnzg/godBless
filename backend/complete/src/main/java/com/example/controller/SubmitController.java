package com.example.controller;

import com.example.entity.*;
import com.example.service.*;
import com.example.util.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("api/v1/submit")
public class SubmitController {
  @Autowired
  private SubmitService submitService;
  @Autowired
  private ExamService examService;
  @RequestMapping("getByUsername")
  public List<SubmitItem> getStudentSubmit(String username){
    return submitService.getStudentSubmit(username);
  }
}
