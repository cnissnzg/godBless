package com.watermark.mapper;

import com.watermark.entity.*;
import org.springframework.stereotype.*;

import java.util.*;

@Repository
public interface MaterialMapper {
  List<Material> getProblemMaterial(int pid);
}
