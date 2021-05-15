package com;

import com.watermark.utils.*;
import org.apache.rocketmq.client.producer.*;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan({"com.watermark.mapper","com.example.mapper"})
@SpringBootApplication
public class Application {

	public static void fuck(){
		int p;
	}

	public static void main(String[] args) {
	  SpringApplication.run(Application.class, args);
	}
}
