package com.example.service;

import com.example.entity.*;
import com.example.mapper.*;
import org.apache.ibatis.annotations.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

import java.util.*;

@Service
public class ReplyService {
  @Autowired
  private ReplyMapper replyMapper;
  public int addReply(Reply reply){
    return replyMapper.addReply(reply);
  }
  public int delReply(int id){
    return replyMapper.delReply(id);
  }
  public Reply getOneReply(int id){
    return replyMapper.getOneReply(id);
  }
  public List<Reply> getReplysOfPost(int pid){
    return replyMapper.getReplysOfPost(pid);
  }
  public int updateText(int id, String text){
    return replyMapper.updateText(id,text);
  }
}
