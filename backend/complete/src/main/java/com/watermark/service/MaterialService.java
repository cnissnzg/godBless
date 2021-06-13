package com.watermark.service;

import com.watermark.entity.*;
import com.watermark.mapper.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

import java.util.*;

@Service
public class MaterialService {
  @Autowired
  private MaterialMapper materialMapper;
  public List<Material> getProblemMaterial(int pid){
    return materialMapper.getProblemMaterial(pid);
  }
  public int add(Material material){
    return materialMapper.add(material);
  }
}
