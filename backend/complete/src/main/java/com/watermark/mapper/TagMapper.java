package com.watermark.mapper;

import org.springframework.stereotype.*;

import java.util.*;

@Repository
public interface TagMapper {
  List<String> getProblemTags(int pid);
}
