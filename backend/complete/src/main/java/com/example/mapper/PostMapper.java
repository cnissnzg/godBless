package com.example.mapper;

import com.example.entity.*;
import org.apache.ibatis.annotations.*;
import org.springframework.security.core.parameters.*;
import org.springframework.stereotype.*;

import java.util.*;
@Repository
public interface PostMapper {
  public int addPost(Post post);
  public int delPost(int id);
  public List<Post> getAllPosts();
  public List<Post> getMyPosts(String username);
  public Post getOnePost(int id);
  public int updateText(@Param("id") int id,@Param("text") String text);
  public int updateLast(@Param("id") int id, @Param("lastReply") int lastReply);

}
