package com.example.mapper;

import com.example.entity.*;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.*;

import java.util.*;

@Repository
public interface ReplyMapper {
  public int addReply(Reply reply);
  public int delReply(int id);
  public Reply getOneReply(int id);
  public List<Reply> getReplysOfPost(int pid);
  public int updateText(@Param("id") int id, @Param("text") String text);
}
