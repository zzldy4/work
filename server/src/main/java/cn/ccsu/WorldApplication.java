package cn.ccsu;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

import java.util.UUID;

@SpringBootApplication
//开启缓存功能
@EnableCaching
@Slf4j
public class WorldApplication {
    public static void main(String[] args) {
        SpringApplication.run(WorldApplication.class, args);
    }
}