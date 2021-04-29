package com.example.service;

import com.example.entity.*;
import com.example.mapper.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Service
public class WatcherService {
  @Autowired
  private WatcherMapper watcherMapper;
  public List<Watcher> getWatcher(String idRoom){
    return watcherMapper.getWatcher(idRoom);
  }
  public int delRoom(String idRoom){
    return watcherMapper.delRoom(idRoom);
  }
  public int addWatcher(String idRoom,String username){
    return watcherMapper.addWatcher(idRoom,username);
  }
  public int delWatcher(String idRoom,String username){
    return watcherMapper.delWatcher(idRoom,username);
  }
}
