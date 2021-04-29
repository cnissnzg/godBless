package com.example.mapper;

import com.example.entity.*;
import org.springframework.stereotype.*;

import java.util.*;
@Repository
public interface VideoMapper {
  public List<Video> getMyVideos(int idcourse);
  public int addVideo(Video video);
}
