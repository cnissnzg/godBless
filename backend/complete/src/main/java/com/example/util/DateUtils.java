package com.example.util;

import java.text.*;
import java.util.*;

public class DateUtils {
  public static Date strToDate(String str) {
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    Date date = null;
    try {
      date = format.parse(str);
    } catch (ParseException e) {
      e.printStackTrace();
    }
    return date;
  }
  public static String dateToStr(Date date){

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    String firstDayOfMonth = sdf.format(date.getTime());
    return firstDayOfMonth;
  }

}
