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
  @Autowired
  private ChallengeService challengeService;
  @Autowired
  private MaterialService materialService;
  @RequestMapping("get")
  public ProblemList getAllPosts(@RequestParam("start")int start, @RequestParam("end")int end){
    return problemService.getProblemList(start, end-start);
  }
  @RequestMapping("getOne")
  public Problem getProblem(@RequestParam("pid") int pid){
    return problemService.getProblem(pid);
  }
  @RequestMapping("getChallenge")
  public List<Challenge> getChallenge(@RequestParam("pid")int pid){
    return challengeService.getProblemChallenge(pid);
  }
  @RequestMapping("getMaterial")
  public List<Material> getMaterial(@RequestParam("pid")int pid){
    return materialService.getProblemMaterial(pid);
  }
}
