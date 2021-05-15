package com.watermark.utils;

import org.apache.rocketmq.client.producer.*;
import org.apache.rocketmq.common.message.*;

public class MqHandler {
  private static DefaultMQProducer producer;
  public static void create(String groupName,String address) throws Exception{
    System.out.println(groupName+","+address);
    producer = new DefaultMQProducer(groupName);
    // 设置NameServer的地址
    producer.setNamesrvAddr(address);
    // 设置消息异步发送失败时的重试次数，默认为 2
    producer.setRetryTimesWhenSendAsyncFailed(3);
    // 设置消息发送超时时间，默认3000ms
    producer.setSendMsgTimeout(6000);
    producer.setVipChannelEnabled(false);
    // 启动Producer实例
    producer.start();
    producer.setRetryTimesWhenSendAsyncFailed(3);

  }

  public static void sendBuildCmd(String content,SendCallback sendCallback) throws Exception{
    Message message = new Message("BUILD",content.getBytes());
    producer.send(message,sendCallback);
  }
  public static void send(String content) throws Exception{
      Message message = new Message("BUILD", content.getBytes());
      producer.send(message);
  }

}
