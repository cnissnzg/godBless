package com.example.mapper;

import com.example.entity.*;
import org.springframework.stereotype.*;

import java.util.*;
@Repository
public interface vDanmuMapper {
  public List<vDanmu> getVideoDanmu(int idvideo);
  public int addDanmu(vDanmu vdanmu);
}
