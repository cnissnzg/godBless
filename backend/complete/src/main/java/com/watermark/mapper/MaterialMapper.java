package com.watermark.mapper;

import com.watermark.entity.*;
import org.springframework.stereotype.*;

import java.util.*;

@Repository
public interface MaterialMapper {
  public List<Material> getProblemMaterial(int pid);
  public List<Material> getAllMaterial(int pid);
  public int add(Material material);
}
