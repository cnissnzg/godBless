package com.example.controller;

import com.example.entity.*;
import com.example.service.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("api/v1/video")
public class VideoController {
  @Autowired
  private VideoService videoService;
  @Autowired
  private CourseService courseService;
  @RequestMapping("getByCourse")
  public List<Video> getMyVideos(int idcourse){
    return videoService.getMyVideos(idcourse);
  }
  @RequestMapping("add")
  public int addVideo(@RequestBody Video video){
    video.setIdcourse(courseService.getId(video.getCourseTitle()));
    return videoService.addVideo(video);
  }
}
