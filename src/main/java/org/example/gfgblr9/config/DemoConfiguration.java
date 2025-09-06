package org.example.gfgblr9.config;

import lombok.extern.slf4j.Slf4j;
import org.example.gfgblr9.services.RedisDriver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Slf4j
@Configuration
public class DemoConfiguration {

     @Bean(name="redisDriverBean")
     public RedisDriver getRedisDriver() {
         log.info("Creating Redis Driver");
         return new RedisDriver("ceee","eee",123);
     }
}
