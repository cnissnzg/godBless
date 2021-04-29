package com.example.util;

import java.util.*;

public class ListAdaptor {
  public static List<Integer> toList(String s){
    String[] tmp = s.split(",");
    ArrayList<Integer> ans = new ArrayList<>(tmp.length);
    for(String t : tmp){
      ans.add(Integer.parseInt(t));
    }
    return ans;
  }
  public static String fromList(List<Integer> tmp){
    String ans = "";
    for(int i = 0;i < tmp.size();i++){
      ans += tmp.get(i).toString();
      if(i != tmp.size()-1){
        ans += ",";
      }
    }
    return ans;
  }
}
