package com.example.service;

import com.example.entity.*;
import com.example.mapper.*;
import org.apache.ibatis.annotations.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

import java.util.*;

@Service
public class PostService {
  @Autowired
  private PostMapper postMapper;
  public int addPost(Post post){
    return postMapper.addPost(post);
  }
  public int delPost(int id){
    return postMapper.delPost(id);
  }
  public List<Post> getAllPosts(){
    return postMapper.getAllPosts();
  }
  public List<Post> getMyPosts(String username){
    return postMapper.getMyPosts(username);
  }
  public Post getOnePost(int id){
    return postMapper.getOnePost(id);
  }
  public int updateText(int id, String text){
    return postMapper.updateText(id,text);
  }
  public int updateLast(int id, int lastReply){
    return postMapper.updateLast(id,lastReply);
  }
}
