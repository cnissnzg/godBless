package com.watermark.mapper;

import com.watermark.entity.*;
import org.springframework.stereotype.*;

import java.util.*;

@Repository
public interface ChallengeMapper {
  public List<Challenge> getProblemChallenge(int pid);
  public List<ChallengeParam> getChallenge();
  public int add(ProblemChallenge problemChallenge);
}
