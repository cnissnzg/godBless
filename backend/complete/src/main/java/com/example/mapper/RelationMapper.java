package com.example.mapper;

import com.example.entity.*;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.*;

import java.util.*;

@Repository
public interface RelationMapper {
  public int addRelation(@Param("student") String student,@Param("teacher") String teacher);
  public int delRelation(@Param("student") String student,@Param("teacher") String teacher);
  public List<Relation> getAllTeacher(@Param("student") String student);
  public List<Relation> getAllStudent(@Param("teacher") String teacher);

}
