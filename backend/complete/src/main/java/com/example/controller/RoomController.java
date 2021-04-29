package com.example.controller;

import com.example.entity.*;
import com.example.service.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("api/v1/Room")
public class RoomController {
  @Autowired
  private RoomService roomService;
  @RequestMapping("get")
  public List<Room> getAllRooms(){
    return roomService.getAllRooms();
  }
  @RequestMapping("add")
  public int addRoom(@RequestBody Room room){
    return roomService.addRoom(room);
  }
  @RequestMapping("del")
  public int delRoom(String idteacher){
    return roomService.delRoom(idteacher);
  }
}
