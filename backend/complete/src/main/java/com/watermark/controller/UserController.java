package com.watermark.controller;

import com.watermark.entity.*;
import com.watermark.service.*;
import com.wf.captcha.*;
import com.wf.captcha.utils.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.security.crypto.bcrypt.*;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.*;
import java.util.*;

@RestController
@RequestMapping("/api/v1/watermark/user")
public class UserController {
  @Autowired
  private UserService userService;

  @Autowired
  private BCryptPasswordEncoder bCryptPasswordEncoder;

  @RequestMapping(value = "register",method = RequestMethod.POST)
  public String addUser(@RequestBody Map<String,String> registerUser){
    User t = new User();
    t.setUid(registerUser.get("uid"));
    t.setPassword(bCryptPasswordEncoder.encode(registerUser.get("pwd")));
    t.setEmail(registerUser.get("email"));
    t.setRole("ROLE_NORMAL");
    return ""+userService.addUser(t);
  }

  @RequestMapping(value = "captcha",method = RequestMethod.GET)
  public String captcha(HttpServletRequest request, HttpServletResponse response) throws Exception{
    SpecCaptcha specCaptcha = new SpecCaptcha(120, 42, 5);
    request.getSession().setAttribute("captcha", specCaptcha.text().toLowerCase());
    System.out.println(specCaptcha.text().toLowerCase());
    return specCaptcha.toBase64();
  }

}
