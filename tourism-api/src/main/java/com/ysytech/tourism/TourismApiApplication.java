package com.ysytech.tourism;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @author AOC
 */
@SpringBootApplication
@MapperScan(basePackages = "com.ysytech.tourism.*.mapper")
public class TourismApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(TourismApiApplication.class, args);
    }

}
