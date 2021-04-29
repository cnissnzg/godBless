package com.example.controller;

import com.example.entity.*;
import com.example.service.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("api/v1/posts")
public class PostController {
  @Autowired
  private PostService postService;
  @RequestMapping("addOne")
  public int addPost(@RequestBody Post post)
  {
    System.out.println(post.getTitle());
    return postService.addPost(post);
  }
  @RequestMapping("delOne")
  public int delPost(int id){
    return postService.delPost(id);
  }
  @RequestMapping("getAll")
  public List<Post> getAllPosts(){
    return postService.getAllPosts();
  }
  @RequestMapping("getByUsername")
  public List<Post> getMyPosts(String username){
    return postService.getMyPosts(username);
  }
  @RequestMapping("getOne")
  public Post getOnePost(int id){
    return postService.getOnePost(id);
  }
  @RequestMapping("updOne")
  public int updateText(@RequestParam("id") int id,@RequestBody String text){
    return postService.updateText(id,text);
  }
}
