package com.example.mapper;

import com.example.entity.*;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.*;

import java.util.*;

@Repository
public interface WatcherMapper {
  public List<Watcher> getWatcher(String idRoom);
  public int delRoom(String idRoom);
  public int addWatcher(@Param("idRoom")String idRoom, @Param("username")String username);
  public int delWatcher(@Param("idRoom")String idRoom, @Param("username")String username);
}
