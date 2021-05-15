package com.watermark.utils;

import java.io.*;
import java.nio.file.*;
import java.nio.file.attribute.*;
import java.util.*;
import java.util.zip.GZIPOutputStream;
import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveOutputStream;
import org.apache.commons.compress.compressors.gzip.*;
import org.apache.commons.compress.utils.IOUtils;
public class CompressHelper {
  public static File pack(List<File> sources, File target) throws IOException {
    FileOutputStream out = null;
    try {
      out = new FileOutputStream(target);
    } catch (FileNotFoundException e1) {
      e1.printStackTrace();
    }
    TarArchiveOutputStream os = new TarArchiveOutputStream(out);
    for (File file : sources) {
      InputStream inputStream = null;
      try {
        os.putArchiveEntry(new TarArchiveEntry(file,file.getName()));
        inputStream = new FileInputStream(file);
        IOUtils.copy(inputStream, os);
        os.closeArchiveEntry();
      } catch (FileNotFoundException e) {
        e.printStackTrace();
      } catch (IOException e) {
        e.printStackTrace();
      }
      if(inputStream!=null){
        inputStream.close();
      }
    }
    if (os != null) {
      try {
        os.flush();
        os.close();
        System.out.println("打包后文件为：" + target);
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    return target;
  }

  public static File compress(File source,String FilePath) {
    File target = new File(FilePath);
    FileInputStream in = null;
    GZIPOutputStream out = null;

    try {
      in = new FileInputStream(source);
      out = new GZIPOutputStream(new FileOutputStream(target));
      byte[] array = new byte[1024];
      int number;
      while ((number = in.read(array, 0, array.length)) != -1) {
        out.write(array, 0, number);
      }
    } catch (FileNotFoundException e) {
      e.printStackTrace();
      return null;
    } catch (IOException e) {
      e.printStackTrace();
      return null;
    } finally {
      if (in != null) {
        try {
          in.close();
          source.delete();
        } catch (IOException e) {
          e.printStackTrace();
          return null;
        }
      }
      if (out != null) {
        try {
          out.close();
          System.out.println("打包后文件为：" + target);
        } catch (IOException e) {
          e.printStackTrace();
          return null;
        }
      }
    }
    return target;
  }

  static public void dirTarGzip(String path,String tarFileName) throws IOException {
    // 被压缩打包的文件夹
    Path source = Paths.get(path);
    //如果不是文件夹抛出异常
    if (!Files.isDirectory(source)) {
      throw new IOException("请指定一个文件夹");
    }

    //OutputStream输出流、BufferedOutputStream缓冲输出流
    //GzipCompressorOutputStream是gzip压缩输出流
    //TarArchiveOutputStream打tar包输出流（包含gzip压缩输出流）
    try (OutputStream fOut = Files.newOutputStream(Paths.get(tarFileName));
         BufferedOutputStream buffOut = new BufferedOutputStream(fOut);
         GzipCompressorOutputStream gzOut = new GzipCompressorOutputStream(buffOut);
         TarArchiveOutputStream tOut = new TarArchiveOutputStream(gzOut)) {
      //遍历文件目录树
      Files.walkFileTree(source, new SimpleFileVisitor<Path>() {

        //当成功访问到一个文件
        @Override
        public FileVisitResult visitFile(Path file,
                                         BasicFileAttributes attributes) throws IOException {

          // 判断当前遍历文件是不是符号链接(快捷方式)，不做打包压缩处理
          if (attributes.isSymbolicLink()) {
            return FileVisitResult.CONTINUE;
          }

          //获取当前遍历文件名称
          Path targetFile = source.relativize(file);

          //将该文件打包压缩
          TarArchiveEntry tarEntry = new TarArchiveEntry(
                  file.toFile(), targetFile.toString());
          tOut.putArchiveEntry(tarEntry);
          Files.copy(file, tOut);
          tOut.closeArchiveEntry();
          //继续下一个遍历文件处理
          return FileVisitResult.CONTINUE;
        }

        //当前遍历文件访问失败
        @Override
        public FileVisitResult visitFileFailed(Path file, IOException exc) {
          System.err.printf("无法对该文件压缩打包为tar.gz : %s%n%s%n", file, exc);
          return FileVisitResult.CONTINUE;
        }

      });
      //for循环完成之后，finish-tar包输出流
      tOut.finish();
    }
  }
}
