package com.watermark.controller;

import com.example.controller.*;
import com.example.util.*;
import com.watermark.config.*;
import com.watermark.entity.*;
import com.watermark.service.*;
import com.watermark.utils.*;
import org.apache.ibatis.annotations.*;
import org.apache.rocketmq.client.exception.*;
import org.apache.rocketmq.client.producer.*;
import org.apache.rocketmq.common.message.*;
import org.apache.rocketmq.remoting.exception.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.util.*;
import org.springframework.web.bind.annotation.*;
import org.apache.commons.io.FileUtils;

import javax.servlet.http.*;
import java.io.*;
import java.nio.charset.*;
import java.nio.file.*;
import java.util.*;

@RestController
@RequestMapping("/api/v1/watermark/algorithm")
public class AlgorithmController {
  @Autowired
  private MyConfig myConfig;
  @Autowired
  private HttpServletRequest request;
  @Autowired
  DefaultMQProducer defaultMQProducer;
  @Autowired
  AlgorithmService algorithmService;

  @Autowired
  private NonStaticResourceHttpRequestHandler nonStaticResourceHttpRequestHandler;

  @GetMapping("/getFile")
  public void getFile(HttpServletRequest request, HttpServletResponse response,@RequestParam("name")String name,@RequestParam("uid")String uid) throws Exception {

    String sourcePath = myConfig.getRoot();
    String realPath = sourcePath+"/" +name+"@"+uid+".tar.gz";

    System.out.println(realPath);
    Path filePath = Paths.get(realPath);
    System.out.println(Files.exists(filePath));
    if (Files.exists(filePath)) {
      int i = 1;//fuck idea
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

  private void createDirs(FileTree fileTree,String dirname) throws Exception{
    for(FileTree tmp:fileTree.getChildren()){
      if(tmp.getType().equals("dir")){
        FileUtils.forceMkdir(new File(dirname+"/"+tmp.getName()));
        createDirs(tmp,dirname+"/"+tmp.getName());
      }
    }
  }
  @RequestMapping(value = "submit",method = RequestMethod.POST)
  public ResponseEntity<Integer> submit(@RequestBody Algorithm algorithm) throws Exception{
    String tokenHeader = request.getHeader("Authorization");
    String token = tokenHeader.replace(JwtTokenUtils.TOKEN_PREFIX, "");
    String filename = algorithm.getName()+"@"+JwtTokenUtils.getUsername(token) +".tar.gz";
    String dirname = myConfig.getRoot()+"/"+algorithm.getName()+"@"+JwtTokenUtils.getUsername(token);

    FileUtils.deleteQuietly(new File(dirname));
    FileUtils.forceMkdir(new File(dirname));
    createDirs(algorithm.getFileTree(),dirname);

    for(String key : algorithm.getCode().keySet()){
      File file = new File(dirname+key);
      FileWriter fileWriter = new FileWriter(file);
      fileWriter.write(algorithm.getCode().get(key));
      fileWriter.close();
    }
    CompressHelper.dirTarGzip(dirname,dirname+".tar.gz");

    if(algorithmService.check(JwtTokenUtils.getUsername(token),algorithm.getName()) == 0){
      algorithmService.addAlgorithm(algorithm);
    }
    test(filename);
    return ResponseEntity.ok().build();
  }

  @RequestMapping(value = "check",method = RequestMethod.GET)
  public ResponseEntity<Integer> submit(@RequestParam("uid") String uid,@RequestParam("name") String name){
    if(algorithmService.check(uid, name) > 0){
      return ResponseEntity.badRequest().build();
    }else{
      return ResponseEntity.ok().build();
    }

  }

  @RequestMapping(value = "test",method = RequestMethod.GET)
  public int test(@RequestParam("name") String name){
    OSSHandler.uploadCode(myConfig.getRoot(),name);
    return 0;
  }

  @RequestMapping(value = "add",method = RequestMethod.POST)
  public int addAlgorithm(@RequestBody Algorithm algorithm){
    return algorithmService.addAlgorithm(algorithm);
  }
  @RequestMapping(value = "getAll",method = RequestMethod.GET)
  public CommonList getAlgorithmList(@RequestParam("start") int start,@RequestParam("end") int end){
    return algorithmService.getAlgorithmList(start,end-start);
  }
  @RequestMapping(value = "getUserAll",method = RequestMethod.GET)
  public CommonList getAlgorithmListByUid(@RequestParam("start") int start,@RequestParam("end") int end,@RequestParam("uid") String uid){
    return algorithmService.getAlgorithmListByUid(start, end-start, uid);
  }
  @RequestMapping(value = "del",method = RequestMethod.GET)
  public int delAlgorithm(@RequestParam("algorithmId") String algorithmId){
    return algorithmService.delAlgorithm(algorithmId);
  }


}
