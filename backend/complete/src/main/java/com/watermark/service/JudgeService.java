package com.watermark.service;

import com.alibaba.fastjson.*;
import com.watermark.entity.*;
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

import java.util.*;

@Service
public class JudgeService {
  public static final Logger LOGGER = LoggerFactory.getLogger(JudgeService.class);
  @Autowired
  DefaultMQProducer defaultMQProducer;

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
  public String startJudge(int algorithmId, String uid,int pid,String filename) throws Exception{
    System.out.println("init");
    JudgeQueue.init();
    System.out.println("sreq");
    Judge judge = JudgeQueue.sendReq_1(uid,algorithmId,pid,filename);
    System.out.println("sta");
    JudgeQueue.putStatus(judge.getRunId(),0);
    String ret = "";
    ret = sendMQ("HTTPServer","start",judge);
    return ret;
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
        //TODO save result
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
      return sendMQ("s2c"+judge.tranIp(),"getcode",judge.getFileName());
    }else{
      return sendMQ("s2c"+judge.tranIp(),"conflict","conflict");
    }
  }

  public String compile(String runId) throws Exception{
    JudgeQueue.putStatus(runId,4);
    Judge judge = JudgeQueue.query(runId);
    return sendMQ("s2c"+judge.tranIp(),String.valueOf(judge.getPort()),"compile");

  }

  public String run(String runId) throws Exception{
    JudgeQueue.putStatus(runId,7);
    Judge judge = JudgeQueue.query(runId);
    return sendMQ("s2c"+judge.tranIp(),String.valueOf(judge.getPort()),"run");
  }
}
