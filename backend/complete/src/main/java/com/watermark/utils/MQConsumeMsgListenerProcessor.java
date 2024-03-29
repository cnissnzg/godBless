package com.watermark.utils;

import com.watermark.entity.*;
import com.watermark.service.*;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.message.MessageExt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @author: lockie
 * @Date: 2020/4/21 11:05
 * @Description: 消费者监听
 */
@Component
public class MQConsumeMsgListenerProcessor implements MessageListenerConcurrently {
  private static final Logger LOGGER = LoggerFactory.getLogger(MQConsumeMsgListenerProcessor.class);
  @Autowired
  private JudgeService judgeService;


  /**
   * 默认msg里只有一条消息，可以通过设置consumeMessageBatchMaxSize参数来批量接收消息
   * 不要抛异常，如果没有return CONSUME_SUCCESS ，consumer会重新消费该消息，直到return CONSUME_SUCCESS
   * @param msgList
   * @param consumeConcurrentlyContext
   * @return
   */
  @Override
  public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgList, ConsumeConcurrentlyContext consumeConcurrentlyContext) {
    if (CollectionUtils.isEmpty(msgList)) {
      LOGGER.info("MQ接收消息为空，直接返回成功");
      return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
    }
    MessageExt messageExt = msgList.get(0);
    LOGGER.info("MQ接收到的消息为：" + messageExt.toString());
    try {
      String topic = messageExt.getTopic();
      String tags = messageExt.getTags();
      String body = new String(messageExt.getBody(), "utf-8");
      judgeService.receive(body);
      LOGGER.info("MQ消息topic={}, tags={}, 消息内容={}", topic,tags,body);
    } catch (Exception e) {
      LOGGER.error("获取MQ消息内容异常{}",e);
    }
    return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
  }
}