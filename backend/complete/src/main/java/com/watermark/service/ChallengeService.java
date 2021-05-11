package com.watermark.service;

import com.watermark.entity.*;
import com.watermark.mapper.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

import java.util.*;

@Service
public class ChallengeService {
  @Autowired
  private ChallengeMapper challengeMapper;
  public List<Challenge> getProblemChallenge(int pid){
    return challengeMapper.getProblemChallenge(pid);
  }

}
