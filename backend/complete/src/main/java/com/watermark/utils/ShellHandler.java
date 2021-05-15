package com.watermark.utils;

import java.io.*;

public class ShellHandler {
  public static int ExecCommand(String command) {
    int retCode = 0;
    try {
      Process process;
      if(System.getProperty("os.name").matches(".*[Ww]indows.*")) {
        process = Runtime.getRuntime().exec(new String[]{ "cmd", "/c", command}, null, null);
      }else{
        process = Runtime.getRuntime().exec(new String[]{"/bin/sh", "-c", command}, null, null);
      }
      retCode = process.waitFor();
      boolean res = ExecOutput(process);
      if(!res) return -1;
    } catch (Exception e) {
      retCode = -1;
    }
    return retCode;
  }
  private static boolean ExecOutput(Process process) throws Exception {
    if (process == null) {
      return false;
    } else {
      InputStreamReader ir = new InputStreamReader(process.getInputStream());
      LineNumberReader input = new LineNumberReader(ir);
      String line;
      StringBuilder output = new StringBuilder();
      while ((line = input.readLine()) != null) {
        output.append(line + "\n");
      }
      input.close();
      ir.close();
      if (output.toString().length() > 0) {
        System.out.println(output.toString());
      }
    }
    return true;
  }
}
