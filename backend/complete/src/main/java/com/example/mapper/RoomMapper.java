package com.example.mapper;

import com.example.entity.*;

import org.springframework.stereotype.*;
import java.util.*;

@Repository
public interface RoomMapper {
  public List<Room> getAllRooms();
  public int addRoom(Room room);
  public int delRoom(String idteacher);
}
