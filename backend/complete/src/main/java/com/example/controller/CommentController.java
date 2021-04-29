package com.example.controller;

import com.example.entity.*;
import com.example.service.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("api/v1/comment")
public class CommentController {
  @Autowired
  private CommentService commentService;
  @RequestMapping("add")
  public int addComment(@RequestBody Comment comment){
    return commentService.addComment(comment);
  }
  @RequestMapping("get")
  public List<Comment> getComment(@RequestParam("idRoom")String idRoom,@RequestParam("time")long time){
    return commentService.getComment(idRoom,time);
  }
}
