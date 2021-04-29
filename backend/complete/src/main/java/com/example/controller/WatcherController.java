package com.example.controller;

import com.example.entity.*;
import com.example.service.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("api/v1/watcher")
public class WatcherController {
  @Autowired
  private WatcherService watcherService;
  @RequestMapping("get")
  public List<Watcher> getWatcher(@RequestParam("idRoom")String idRoom){
    return watcherService.getWatcher(idRoom);
  }
  @RequestMapping("delRoom")
  public int delRoom(@RequestParam("idRoom")String idRoom){
    return watcherService.delRoom(idRoom);
  }
  @RequestMapping("add")
  public int addWatcher(@RequestParam("idRoom")String idRoom,@RequestParam("username") String username){
    return watcherService.addWatcher(idRoom,username);
  }
  @RequestMapping("delWatcher")
  public int delWatcher(@RequestParam("idRoom")String idRoom,@RequestParam("username")String username){
    return watcherService.delWatcher(idRoom,username);
  }
}
