package com.example.controller;
import com.example.config.*;
import com.example.controller.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.util.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.*;

import javax.servlet.http.*;
import java.io.*;
import java.nio.charset.*;
import java.nio.file.*;
import java.util.*;

@RestController
@RequestMapping("/file/video")

public class FileRestController {
  @Autowired
  private NonStaticResourceHttpRequestHandler nonStaticResourceHttpRequestHandler;

  @GetMapping("/get")
  public void videoPreview(HttpServletRequest request, HttpServletResponse response,@RequestParam("filename")String filename) throws Exception {

    //假如我把视频1.mp4放在了static下的video文件夹里面
    //sourcePath 是获取resources文件夹的绝对地址
    //realPath 即是视频所在的磁盘地址
    String sourcePath = myConfig.path;
    String realPath = sourcePath +filename;

    System.out.println(realPath);
    Path filePath = Paths.get(realPath);
    System.out.println(Files.exists(filePath));
    if (Files.exists(filePath)) {
      String mimeType = Files.probeContentType(filePath);
      if (!StringUtils.isEmpty(mimeType)) {
        response.setContentType(mimeType);
      }
      request.setAttribute(NonStaticResourceHttpRequestHandler.ATTR_FILE, filePath);
      nonStaticResourceHttpRequestHandler.handleRequest(request, response);
    } else {
      response.setStatus(HttpServletResponse.SC_NOT_FOUND);
      response.setCharacterEncoding(StandardCharsets.UTF_8.toString());
    }
  }
  @RequestMapping("/upload")
  @ResponseBody
  public String upload(HttpServletRequest request, HttpServletResponse response,@RequestParam("file") MultipartFile file) {
    if (file.isEmpty()) {
      return "上传失败，请选择文件";
    }

    String fileName = file.getOriginalFilename();
    String filePath = myConfig.path;
    File dest = new File(filePath + fileName);
    System.out.println(dest.getAbsolutePath());
    try {
      file.transferTo(dest);
      return "上传成功";
    } catch (IOException e) {
      e.printStackTrace();
    }
    return "上传失败！";
  }

}