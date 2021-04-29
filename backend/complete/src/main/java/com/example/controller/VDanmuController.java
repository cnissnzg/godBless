package com.example.controller;

import com.example.entity.*;
import com.example.service.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("api/v1/vDanmu")
public class VDanmuController {
  @Autowired
  private vDanmuService danmuService;
  @RequestMapping("get")
  public List<vDanmu> getVideoDanmu(int idvideo){
    return danmuService.getVideoDanmu(idvideo);
  }
  @RequestMapping("add")
  public int addDanmu(@RequestBody vDanmu vdanmu){
    return danmuService.addDanmu(vdanmu);
  }
}
