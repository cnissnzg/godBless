package com.watermark.controller;

import com.example.config.*;
import com.example.entity.*;
import com.watermark.entity.*;
import com.watermark.entity.Problem;
import com.watermark.service.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.*;

import javax.servlet.http.*;
import java.io.*;
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

  @CrossOrigin
  @RequestMapping("/upload")
  @ResponseBody
  public String upload(HttpServletRequest request, HttpServletResponse response, @RequestParam("file") MultipartFile file) {
    if (file.isEmpty()) {
      return "上传失败，请选择文件!";
    }

    String fileName = file.getOriginalFilename();
    String filePath = "E:/null/";
    File dest = new File(filePath + fileName);
    System.out.println(dest.getAbsolutePath());
    try {
      file.transferTo(dest);
      return "上传成功";
    } catch (IOException e) {
      e.printStackTrace();
    }
    return "上传失败！";
  }
}
