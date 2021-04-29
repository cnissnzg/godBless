package com.example.service;

import com.example.entity.*;
import com.example.mapper.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

import java.util.*;

@Service
public class VideoService {
  @Autowired
  private VideoMapper videoMapper;
  public List<Video> getMyVideos(int idcourse){return videoMapper.getMyVideos(idcourse);}
  public int addVideo(Video video){return videoMapper.addVideo(video);}
}
