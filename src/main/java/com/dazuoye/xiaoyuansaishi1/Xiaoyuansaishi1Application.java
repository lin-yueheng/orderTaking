package com.dazuoye.xiaoyuansaishi1;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Slf4j
//@MapperScan("com.dazuoye.xiaoyuansaishi1.mapper")
public class Xiaoyuansaishi1Application {

    public static void main(String[] args) {
        SpringApplication.run(Xiaoyuansaishi1Application.class, args);
        log.info("success");
    }

}
