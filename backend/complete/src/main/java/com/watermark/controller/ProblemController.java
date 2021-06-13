package com.watermark.controller;

import com.alibaba.fastjson.*;
import com.example.config.*;
import com.example.entity.*;
import com.google.gson.*;
import com.watermark.entity.*;
import com.watermark.entity.Problem;
import com.watermark.service.*;
import org.apache.commons.io.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
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

  @RequestMapping(value = "getParam",method = RequestMethod.GET)
  public List<ChallengeOut> getChallenge(){
    List<ChallengeOut> res = new ArrayList<>();
    Map<String,List<Map<String,String>>> hash = new HashMap<>();
    Set<String> flags = new HashSet<>();
    List<ChallengeParam> input = challengeService.getChallenge();

    for(ChallengeParam challengeParam:input){
      String key = challengeParam.getCid();
      if(!hash.containsKey(key)){
        hash.put(key,new ArrayList<Map<String,String>>());
      }
      Map<String,String> kv = new HashMap<>();
      kv.put("name",challengeParam.getName());
      kv.put("domain",challengeParam.getDomain());
      hash.get(key).add(kv);
    }
    for(ChallengeParam challengeParam:input) {
      String key = challengeParam.getCid();
      if(!flags.contains(key)){
        flags.add(key);
        ChallengeOut challengeOut = new ChallengeOut();
        challengeOut.setName(key);
        challengeOut.setParams(hash.get(key));
        res.add(challengeOut);
      }
    }
    return res;
  }

  @CrossOrigin
  @RequestMapping("/upload")
  public ResponseEntity<String> upload(HttpServletRequest request, HttpServletResponse response, @RequestParam("file") MultipartFile file) {
    if (file.isEmpty()) {
      return new ResponseEntity<>("文件为空", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    String fileName = file.getOriginalFilename();
    String filePath = "/root/watermark/true_library/";
    File dest = new File(filePath + fileName);

    System.out.println(dest.getAbsolutePath());
    try {
      if(file.getContentType().startsWith("video")){
        Runtime.getRuntime().exec ("python3 /root/watermark/godBless/video2gif.py /root/watermark/true_library "+fileName);
        Thread.sleep(3000);
      }
      file.transferTo(dest);
      return new ResponseEntity<>("上传成功",HttpStatus.OK);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return new ResponseEntity<>("上传失败", HttpStatus.INTERNAL_SERVER_ERROR);
  }
  @RequestMapping(value = "add",method = RequestMethod.POST)
  public ResponseEntity<String> add(@RequestBody JSONObject obj){
    System.out.println(problemService.getPid());
    int pid = problemService.getPid()+1;
    Problem problem = new Problem();
    problem.setPid(pid);
    problem.setUname(obj.getString("uid"));
    problem.setTitle(obj.getString("title"));
    problem.setType(Integer.parseInt(obj.getString("type")));
    problem.setMaterialCnt(obj.getJSONArray("fileList").size());
    problem.setTestCnt(obj.getJSONArray("data").size());
    problem.setDescription(obj.getString("description"));

    problem.setTimeLimit(Integer.valueOf(obj.getString("timeLimit")));
    problem.setMemoryLimit(Integer.valueOf(obj.getString("memoryLimit")));
    problem.setBitErrorLimit(Double.valueOf(obj.getString("bitErrorLimit"))/100.0);
    problem.setQualityLimit(Integer.valueOf(obj.getString("qualityLimit")));
    problemService.add(problem);
    problemService.addLimit(problem);

    JSONArray jsonArray = obj.getJSONArray("fileList");
    int n = jsonArray.size();
    for(int i = 0;i < n;i++){
      JSONObject sub = jsonArray.getJSONObject(i);
      Material material = new Material();
      material.setPid(pid);
      String raw = sub.getString("type");
      if(raw.startsWith("video")){
        material.setType(2);
      }else {
        material.setType(1);
      }
      String wholeName = sub.getString("name");
      System.out.println(wholeName);
      material.setFilename(wholeName.split("\\.")[0]);
      material.setSuffix(wholeName.split("\\.")[1]);
      materialService.add(material);
    }

    jsonArray = obj.getJSONArray("data");
    n = jsonArray.size();
    for(int i = 0;i < n;i++) {
      JSONObject sub = jsonArray.getJSONObject(i);
      ProblemChallenge problemChallenge = new ProblemChallenge();
      problemChallenge.setPid(pid);
      problemChallenge.setCid(sub.getString("cid"));
      problemChallenge.setParams(sub.getJSONArray("params").toJSONString());
      challengeService.add(problemChallenge);
    }
    try {
      if(problem.getType()==0) {
        FileUtils.copyDirectory(new File("/root/input/library"),new File("/root/input/photo/"+String.valueOf(problem.getPid())));
      }else{
        FileUtils.copyDirectory(new File("/root/input/library"),new File("/root/input/video/"+String.valueOf(problem.getPid())));
      }
    }catch (Exception e){
      return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    return new ResponseEntity<>("success", HttpStatus.OK);
  }
}
