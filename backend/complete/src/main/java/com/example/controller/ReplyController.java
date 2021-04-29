package com.example.controller;

import com.example.entity.*;
import com.example.service.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("api/v1/reply")
public class ReplyController {
  @Autowired
  private ReplyService replyService;
  @Autowired
  private PostService postService;
  @RequestMapping("addOne")
  public int addReply(@RequestBody Reply reply){
    return replyService.addReply(reply);
  }
  @RequestMapping("delOne")
  public int delReply(int id){
    return replyService.delReply(id);
  }
  @RequestMapping("getOne")
  public Reply getOneReply(int id){
    return replyService.getOneReply(id);
  }
  @RequestMapping("getByPost")
  public List<Reply> getReplysOfPost(int pid){
    return replyService.getReplysOfPost(pid);
  }
  @RequestMapping("updOne")
  public int updateText(@RequestParam("id") int id, @RequestBody String text) {
    return replyService.updateText(id, text);
  }
}
