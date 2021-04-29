package com.example.mapper;

import com.example.entity.*;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.*;

@Repository
public interface TeacherMapper {
  public int insertTeacher(Teacher teacher);
  public int deleteTeacher(String username);
  public Teacher getOneTeacher(String username);
  public int updateTeacher(@Param("username") String username,@Param("teacher") Teacher teacher);
}
