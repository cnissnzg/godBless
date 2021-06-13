package com.watermark.controller;

import com.example.controller.*;
import com.watermark.config.*;
import com.watermark.entity.*;
import com.watermark.service.*;
import com.watermark.utils.*;
import org.apache.rocketmq.client.consumer.*;
import org.apache.rocketmq.client.exception.*;
import org.apache.rocketmq.client.producer.*;
import org.apache.rocketmq.common.message.*;
import org.apache.rocketmq.remoting.exception.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.util.*;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.*;
import java.nio.charset.*;
import java.nio.file.*;
import java.util.*;

@RestController
@RequestMapping("/api/v1/watermark/judge")
public class JudgeController {
  @Autowired
  private MyConfig myConfig;
  @Autowired
  JudgeService judgeService;
  @Autowired
  AlgorithmService algorithmService;
  @Autowired
  private NonStaticResourceHttpRequestHandler nonStaticResourceHttpRequestHandler;
  @Autowired
  DefaultMQPushConsumer defaultMQPushConsumer;
  @GetMapping("/hi")
  public ResponseEntity<String> sayHi(@RequestParam("runId")String runId){
    System.out.println("hear you baby!"+runId);
    if(JudgeQueue.stillAlive(runId)){
      return new ResponseEntity<>("",HttpStatus.OK);
    }else{
      return new ResponseEntity<>("404",HttpStatus.BAD_REQUEST);
    }

  }

  @GetMapping("/godBless")
 public ResponseEntity<String> startJudge(@RequestParam("algorithmId") int algorithmId,
                                           @RequestParam("uid") String uid, @RequestParam("pid") int pid){
    String ret="";
    try {
      ret = judgeService.startJudge(algorithmId,uid,pid,algorithmService.getAlgorithmById(algorithmId).getFileName());
    }catch (Exception e){
      return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
    return new ResponseEntity<>(ret,HttpStatus.OK);
 }

 @GetMapping("/getCode")
 public void getCode(HttpServletRequest request, HttpServletResponse response, @RequestParam("runId")String runId,@RequestParam("algorithmId")int algorithmId,@RequestParam("IP")String ip) throws Exception{
    if(!JudgeQueue.check(runId,ip)){
      response.setStatus(HttpServletResponse.SC_CONFLICT);
      return;
    }
    Algorithm algorithm = algorithmService.getAlgorithmById(algorithmId);
   String sourcePath = myConfig.getRoot();
   String realPath = sourcePath+"/" +algorithm.getName()+"@"+algorithm.getUid()+".tar.gz";

   System.out.println(realPath);
   Path filePath = Paths.get(realPath);
   System.out.println(Files.exists(filePath));
   if (Files.exists(filePath)) {
     int i = 2;//fuck idea
     String mimeType = Files.probeContentType(filePath);
     if (!StringUtils.isEmpty(mimeType)) {
       response.setContentType(mimeType);
     }
     request.setAttribute(NonStaticResourceHttpRequestHandler.ATTR_FILE, filePath);
     nonStaticResourceHttpRequestHandler.handleRequest(request, response);
   } else {
     response.setStatus(HttpServletResponse.SC_NOT_FOUND);
     response.setCharacterEncoding(StandardCharsets.UTF_8.toString());
   }

 }

  @GetMapping("/compile")
  public ResponseEntity<String> compile(@RequestParam("runId") String runId){
    String ret="";
    try {
      ret = judgeService.compile(runId);
    }catch (Exception e){
      return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
    return new ResponseEntity<>(ret,HttpStatus.OK);
  }

  @GetMapping("/run")
  public ResponseEntity<String> run(@RequestParam("runId") String runId){
    String ret="";
    try {
      ret = judgeService.run(runId);
    }catch (Exception e){
      return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
    return new ResponseEntity<>(ret,HttpStatus.OK);
  }

  @GetMapping("/debug")
  public ResponseEntity<RunJson> debug(@RequestParam("algorithmId") int algorithmId,
                                           @RequestParam("uid") String uid, @RequestParam("pid") int pid){
    Judge judge = new Judge();
    RunJson runJson = new RunJson();
    try {
      judge = judgeService._debug(algorithmId,uid,pid,algorithmService.getAlgorithmById(algorithmId).getFileName());
    }catch (Exception e){
      return new ResponseEntity<>(runJson, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    return new ResponseEntity<>(judgeService.getRunBody(judge),HttpStatus.OK);
  }

  @GetMapping("/list")
  public ResponseEntity<List<Map<String,String>>> getList(){
    List<Judge> res = new ArrayList<>();
    try {
      res = judgeService.query();
    }catch (Exception e){
      return new ResponseEntity<>(judgeService.tranJudge(res), HttpStatus.INTERNAL_SERVER_ERROR);
    }
    return new ResponseEntity<>(judgeService.tranJudge(res),HttpStatus.OK);
  }

  @GetMapping("/report")
  public ResponseEntity<String> getReport(@RequestParam("runId") String runId){
    String res = "";
    try {
      res = OSSHandler.load(runId);
    }catch (Exception e){
      return new ResponseEntity<>(res, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    return new ResponseEntity<>(res, HttpStatus.OK);
  }
}
