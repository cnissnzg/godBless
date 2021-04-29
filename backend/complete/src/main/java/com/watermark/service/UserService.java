package com.watermark.service;

import com.example.entity.*;
import com.watermark.entity.*;
import com.watermark.entity.User;
import com.watermark.mapper.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.*;

@Service
public class UserService  implements UserDetailsService {
  @Autowired
  UserMapper userMapper;
  public User getUser(String uid){
    return userMapper.getUser(uid);
  }
  public int addUser(User user){
    return userMapper.addUser(user);
  }

  @Override
  public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
    User user = userMapper.getUser(s);
    return new JwtUser(user);
  }
}
