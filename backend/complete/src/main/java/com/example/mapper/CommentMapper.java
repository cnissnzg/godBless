package com.example.mapper;

import com.example.entity.*;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.*;

import java.util.*;

@Repository
public interface CommentMapper {
  public int addComment(Comment comment);
  public List<Comment> getComment(@Param("idRoom") String idRoom,@Param("time") long time);
}
