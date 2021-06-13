package com.watermark.utils;

import com.watermark.entity.*;

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.util.concurrent.locks.*;

class WatchThread implements Runnable{
  public void run(){
    while (true) {
      try {
        Thread.sleep(50000);
        JudgeQueue.emit();
        System.out.println(System.currentTimeMillis()+" :");
        JudgeQueue.print();
      }catch (Exception e){
        e.printStackTrace();

      }
    }
  }
};
public class JudgeQueue {
  private static Comparator<Judge> cmp = new Comparator<Judge>() {
    @Override
    public int compare(Judge o1, Judge o2) {
      return Long.compare(o2.getTimeStamp(),o1.getTimeStamp());
    }
  };
  private static long BEAR_TIME = 100000;
  private static PriorityBlockingQueue<Judge> heap = new PriorityBlockingQueue<>(100,cmp);
  private static HashMap<String,Judge> enovy =new HashMap<>();
  private static Map<String,String> hasReceived = new ConcurrentHashMap<>();
  public static Map<String,Integer> status4Query = new ConcurrentHashMap<>();
  private static ReentrantLock lock = new ReentrantLock();
  private static AtomicBoolean hasInit = new AtomicBoolean(false);
  public static Thread watcher;

  public static void init(){
    if(hasInit.compareAndSet(false,true)){
      lock.lock();
      heap.clear();
      hasReceived.clear();
      lock.unlock();
      WatchThread watchThread = new WatchThread();
      watcher = new Thread(watchThread);
      watcher.start();
      System.out.println("here we go!");
    }
  }
  public synchronized static boolean putStatus(String runId,int status){
    if(status4Query.containsKey(runId)){
      if(status4Query.get(runId) >= status){
        return false;
      }
    }
    status4Query.put(runId,status);
    return true;
  }
  public synchronized static int getStatus(String runId){
    return status4Query.get(runId);
  }

  public synchronized static boolean check(String runId,String IP){

    if(hasReceived.containsKey(runId) && !hasReceived.get(runId).equals(IP)){
      System.out.println(hasReceived.get(runId));
      return false;
    }
    hasReceived.put(runId,IP);
    return true;
  }
  private static void register(Judge judge){
    lock.lock();
    enovy.put(judge.getRunId(),judge);
    lock.unlock();
  }
  public static Judge query(String id){
    lock.lock();
    Judge judge = enovy.get(id);
    lock.unlock();
    return judge;

  }
  private static void update(String runId,Judge judge){
    lock.lock();
    enovy.put(runId,judge);
    lock.unlock();
  }
  public static List<Judge> getInfo(){
    lock.lock();
    HashMap<String,Judge> cpy = new HashMap<>();
    cpy.putAll(enovy);
    lock.unlock();
    List<Judge> res = new ArrayList<>();
    for(String key:cpy.keySet()){
      Judge tmp = cpy.get(key);
      tmp.setState(getStatus(key));
      res.add(tmp);
    }
    return res;
  }
  private static void loseControl(String runId){
    lock.lock();
    Judge judge = enovy.get(runId);
    judge.setState(-1);
    lock.unlock();
  }
  public static boolean stillAlive(String id){
    Judge judge = query(id);
    if(judge==null){
      return false;
    }

    judge.setTimeStamp(System.currentTimeMillis());
    lock.lock();
    if(!judge.isInJudge()){
      judge.setInJudge(true);
      heap.add(judge);
    }
    enovy.put(id,judge);
    lock.unlock();
    return true;
  }


  public static void emit(){
    long timnow = System.currentTimeMillis();
    lock.lock();
    int cnt = heap.size();
    while (!heap.isEmpty() && cnt >= 0){
      Judge top = heap.peek();
      Judge inDB = enovy.get(top.getRunId());

      if(inDB.getState() == 0){
        heap.poll();
      }else{
        cnt--;
      }
      if(inDB.getTimeStamp() != top.getTimeStamp()){
        top.setTimeStamp(inDB.getTimeStamp());
        heap.poll();
        heap.add(top);
      }else if(System.currentTimeMillis() - top.getTimeStamp() > BEAR_TIME){
        heap.poll();
        loseControl(top.getRunId());
      }else{
        break;
      }
    }
    lock.unlock();
  }
  public static void print(){
    lock.lock();
    System.out.println(enovy);
    System.out.println(heap);
    System.out.println(hasReceived);
    System.out.println(status4Query);
    lock.unlock();
  }

  public static Judge sendReq_1(String uid,int algorithmId,Integer pid,String filename){
    Judge judge = new Judge();
    String runId = UUID.randomUUID().toString();
    judge.setState(0);
    judge.setRunId(runId);
    judge.setAlgorithmId(algorithmId);
    judge.setUid(uid);
    judge.setFileName(filename);
    if(pid != null) judge.setPid(pid);
    register(judge);
    return judge;
  }

  public static void build_1_2(String runId,String ip,Integer port){
    Judge judge = query(runId);
    if(ip != null) judge.setIp(ip);
    if(port != null) judge.setPort(port);
    judge.setState(2);
    update(runId,judge);
    stillAlive(runId);
  }

  public static void buildFailed_1_n2(String runId){
    Judge judge = query(runId);
    judge.setState(-2);
    update(runId,judge);
    stillAlive(runId);
  }

  public static void compile_2_3(String runId){
    Judge judge = query(runId);
    judge.setState(3);
    update(runId,judge);
    stillAlive(runId);
  }

  public static void compileFailed_2_n3(String runId){
    Judge judge = query(runId);
    judge.setState(-3);
    update(runId,judge);
    stillAlive(runId);
  }

  public static void run_3_4(String runId){
    Judge judge = query(runId);
    judge.setState(3);

    update(runId,judge);
    stillAlive(runId);
  }

  public static void runFailed_3_n4(String runId){
    Judge judge = query(runId);
    judge.setState(-4);
    update(runId,judge);
    stillAlive(runId);
  }

  public static void success_4_0(String runId){
    Judge judge = query(runId);
    judge.setState(0);
    update(runId,judge);
    stillAlive(runId);
  }

}