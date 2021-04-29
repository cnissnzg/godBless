package com.example.service;

import com.example.entity.*;
import com.example.mapper.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Service
public class CommentService {
  @Autowired
  private CommentMapper commentMapper;
  public int addComment(@RequestBody Comment comment){
    return commentMapper.addComment(comment);
  }
  public List<Comment> getComment(String idRoom,long time){
    return commentMapper.getComment(idRoom,time);
  }
}
