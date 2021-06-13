package com.watermark.service;

import com.alibaba.fastjson.*;
import com.google.gson.*;
import com.watermark.entity.*;
import com.watermark.mapper.*;
import com.watermark.utils.*;
import org.apache.rocketmq.client.consumer.*;
import org.apache.rocketmq.client.exception.*;
import org.apache.rocketmq.client.producer.*;
import org.apache.rocketmq.common.message.*;
import org.apache.rocketmq.remoting.exception.*;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;

import java.text.*;
import java.util.*;

@Service
public class JudgeService {
  public static final Logger LOGGER = LoggerFactory.getLogger(JudgeService.class);
  @Autowired
  DefaultMQProducer defaultMQProducer;
  @Autowired
  MaterialMapper materialMapper;
  @Autowired
  ProblemMapper problemMapper;
  @Autowired
  ChallengeMapper challengeMapper;
  @Autowired
  AlgorithmMapper algorithmMapper;

  public RunJson getRunBody(Judge judge){
    RunJson runJson = new RunJson();
    runJson.setRunId(judge.getRunId());
    Map<String,String> waternark = new HashMap<>();
    waternark.put("origin","http://39.105.21.114:11451/watermark/baboon.png");
    waternark.put("name","watermark.png");
    runJson.setWatermark(waternark);

    Problem problem = problemMapper.getProblem(judge.getPid());
    List<Material> rawMat = materialMapper.getProblemMaterial(judge.getPid());
    List<Map<String,String>> materialMapList = new ArrayList<>();
    for(Material material:rawMat){
      Map<String,String> tmp = new HashMap<>();
      tmp.put("name",material.getFilename()+"."+material.getSuffix());
      if(material.getType()==1){
        tmp.put("url","http://39.105.21.114:11451/photo/"+String.valueOf(judge.getPid())+"/"+tmp.get("name"));
      }
      materialMapList.add(tmp);

    }
    runJson.setMaterial(materialMapList);
    runJson.setType(problem.getType());

    List<Challenge> challengeList = challengeMapper.getProblemChallenge(judge.getPid());
    List<Map<String,Object>> challengeMapList = new ArrayList<>();
    for(Challenge challenge:challengeList){
      Map<String,Object> tmp = new HashMap<>();
      tmp.put("cid",challenge.getCid());
      JSONArray jsonArray = JSON.parseArray(challenge.getParams());
      int n = jsonArray.size();
      for(int i = 0;i < n;i++){
        JSONObject jsonObject = jsonArray.getJSONObject(i);
        tmp.put(jsonObject.getString("name"),jsonObject.get("value"));
      }
      challengeMapList.add(tmp);
    }
    runJson.setChallenge(challengeMapList);
    return runJson;
  }

  private String sendMQ(String topic,String tags,Object obj) throws InterruptedException, RemotingException, MQClientException, MQBrokerException {
    Message sendMsg = new Message(topic, tags, obj.toString().getBytes());
    // 默认3秒超时
    SendResult sendResult = defaultMQProducer.send(sendMsg);
    return sendResult.toString();
  }
  public static String requestTypeName(int type){
    switch (type){
      case 0:
        return "请求已提交";
      case 1:
        return "代码发送至虚拟环境";
      case 2:
        return "构建成功";
      case 3:
        return "构建失败";
      case 4:
        return "正在编译";
      case 5:
        return "编译成功";
      case 6:
        return "编译失败";
      case 7:
        return "正在运行";
      case 8:
        return "嵌入水印成功";
      case 9:
        return "鲁棒性脚本运行成功";
      case 10:
        return "水印提取成功";
      case 11:
        return "测评结束";
      case 12:
        return "运行失败";
      default:
        return "未知错误";
    }
  }
  public Map<String,String> tranJudge(Judge judge){
    Map<String,String> res = new HashMap<>();
    Algorithm algorithm = algorithmMapper.getAlgorithmById(judge.getAlgorithmId());
    Problem problem = problemMapper.getProblem(judge.getPid());
    SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    String dateStr = dateformat.format(judge.getTimeStamp());
    res.put("runId",judge.getRunId());
    res.put("state",String.valueOf(JudgeQueue.getStatus(judge.getRunId())));
    res.put("status",requestTypeName(JudgeQueue.getStatus(judge.getRunId())));
    res.put("callerUid",judge.getUid());
    res.put("authorUid",algorithm.getUid());
    res.put("algorithmName",algorithm.getName());
    res.put("title",String.valueOf(problem.getPid())+" - "+problem.getTitle());
    res.put("ctime",dateStr);
    return res;
  }
  public List<Map<String,String>> tranJudge(List<Judge> judges){
    List<Map<String,String>> res = new ArrayList<>();
    int n = judges.size();
    for(int i = 0;i < n;i++){
      res.add(tranJudge(judges.get(i)));
    }
    return res;
  }
  public String startJudge(int algorithmId, String uid,int pid,String filename) throws Exception{
    JudgeQueue.init();
    Judge judge = JudgeQueue.sendReq_1(uid,algorithmId,pid,filename);
    JudgeQueue.putStatus(judge.getRunId(),0);
    String ret = "";
    ret = sendMQ("HTTPServer","start",judge);
    return ret;
  }

  public Judge _debug(int algorithmId, String uid,int pid,String filename) throws Exception{
    JudgeQueue.init();
    return JudgeQueue.sendReq_1(uid,algorithmId,pid,filename);
  }

  public void receive(String msg){
    JudgeStatus judgeStatus = JSON.parseObject(msg,JudgeStatus.class);
    switch (judgeStatus.getType()){
      case 0:
        try {
          sendCode(judgeStatus.getRunId(), judgeStatus.getDetail().get("ip"));
        }catch (Exception e){
          e.printStackTrace();
        }
        break;
      case 2:
        Map<String,String> data = judgeStatus.getDetail();
        JudgeQueue.build_1_2(judgeStatus.getRunId(),data.get("ip"),Integer.parseInt(data.get("port")));
        break;
      case 3:
        JudgeQueue.buildFailed_1_n2(judgeStatus.getRunId());
        break;
      case 5:
        JudgeQueue.compile_2_3(judgeStatus.getRunId());
        break;
      case 6:
        JudgeQueue.compileFailed_2_n3(judgeStatus.getRunId());
        break;
      case 8:
      case 9:
      case 10:
        JudgeQueue.run_3_4(judgeStatus.getRunId());
        break;
      case 11:
        JudgeQueue.success_4_0(judgeStatus.getRunId());
        OSSHandler.save(judgeStatus.getRunId(),msg);
        break;
      case 12:
        JudgeQueue.runFailed_3_n4(judgeStatus.getRunId());
        break;
      default:
        LOGGER.error("who tell you to do fuck? "+judgeStatus.toString());
    }
    JudgeQueue.putStatus(judgeStatus.getRunId(),judgeStatus.getType());
  }

  private String sendCode(String runId,String ip) throws Exception{
    Judge judge = JudgeQueue.query(runId);
    System.out.println(judge);
    judge.setIp(ip);
    if(JudgeQueue.check(judge.getRunId(),judge.getIp())){
      System.out.println("s2c"+judge.tranIp());
      return sendMQ("s2c"+judge.tranIp(),"getcode",judge.getRunId());
    }else{
      return sendMQ("s2c"+judge.tranIp(),"conflict","conflict");
    }
  }

  public String compile(String runId) throws Exception{
    if(!JudgeQueue.putStatus(runId,4)){
      throw new Exception("Duplicated");
    }
    Judge judge = JudgeQueue.query(runId);
    return sendMQ("s2c"+judge.tranIp(),"compile",judge.getRunId());

  }

  public String run(String runId) throws Exception{
    if(!JudgeQueue.putStatus(runId,7)){
      throw new Exception("Duplicated");
    }
    Judge judge = JudgeQueue.query(runId);
    return sendMQ("s2c"+judge.tranIp(),"run",JSON.toJSON(getRunBody(judge)));
  }

  public List<Judge> query(){
    return  JudgeQueue.getInfo();
  }

}
