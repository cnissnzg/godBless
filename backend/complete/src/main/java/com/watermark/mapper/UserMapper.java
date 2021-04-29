package com.watermark.mapper;

import com.watermark.entity.*;
import org.springframework.stereotype.*;

@Repository
public interface UserMapper {
  public User getUser(String uid);
  public int addUser(User user);
}
