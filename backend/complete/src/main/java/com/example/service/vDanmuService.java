package com.example.service;

import com.example.entity.*;
import com.example.mapper.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

import java.util.*;

@Service
public class vDanmuService {
  @Autowired
  private vDanmuMapper vdanmuMapper;
  public List<vDanmu> getVideoDanmu(int idvideo){
    return vdanmuMapper.getVideoDanmu(idvideo);
  }
  public int addDanmu(vDanmu vdanmu){
    return vdanmuMapper.addDanmu(vdanmu);
  }
}
