package com.example.controller;

import org.springframework.security.access.prepost.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tasks")
@PreAuthorize("hasAnyRole('ADMIN','NORMAL')")
public class SafeTestController {
  @GetMapping
  //@PreAuthorize("hasRole('ADMIN')")
  public String listTasks(){
    return "任务列表";
  }

  @PostMapping
  public String newTasks(){
    return "创建了一个新的任务";
  }

  @PutMapping("/{taskId}")
  public String updateTasks(@PathVariable("taskId")Integer id){
    return "更新了一下id为:"+id+"的任务";
  }

  @DeleteMapping("/{taskId}")
  //@PreAuthorize("hasRole('ADMIN')")
  public String deleteTasks(@PathVariable("taskId")Integer id){
    return "删除了id为:"+id+"的任务";
  }
}
