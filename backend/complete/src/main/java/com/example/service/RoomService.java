package com.example.service;

import com.example.entity.*;
import com.example.mapper.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

import java.util.*;

@Service
public class RoomService {
  @Autowired
  private RoomMapper roomMapper;
  public List<Room> getAllRooms(){
    return roomMapper.getAllRooms();
  }
  public int addRoom(Room room){
    return roomMapper.addRoom(room);
  }
  public int delRoom(String idteacher){
    return roomMapper.delRoom(idteacher);
  }
}
