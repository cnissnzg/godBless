package com.watermark.controller;

import com.example.entity.*;
import com.watermark.entity.*;
import com.watermark.entity.Problem;
import com.watermark.service.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/v1/watermark/problem")
public class ProblemController {
  @Autowired
  private ProblemService problemService;
  @RequestMapping("get")
  public ProblemList getAllPosts(@RequestParam("start")int start, @RequestParam("end")int end){
    return problemService.getProblemList(start, end-start);
  }
}
