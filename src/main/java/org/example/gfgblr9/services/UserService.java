package org.example.gfgblr9.services;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserService implements ApplicationContextAware {
    public UserService() {
        log.info("UserService getting initiated");
    }

    ApplicationContext applicationContext;


    @Autowired
    public UserService(@Qualifier("paymentServiceNew") PaymentInterface paymentInterface) {
        this.paymentInterface = paymentInterface;
        log.info("UserService getting initiated");

    }

    private PaymentInterface paymentInterface;


    private RedisDriver redisDriver;

    public void setRedisDriver() {
        this.redisDriver=  (RedisDriver)applicationContext.getBean("redisDriverBean");
        log.info(applicationContext.getBean("paymentServiceNew").toString());
    }

    public RedisDriver getRedisDriver() {
        return this.redisDriver;
    }

    @PostConstruct
    public void init() {
        this.redisDriver=  (RedisDriver)applicationContext.getBean("redisDriverBean");
        log.info(applicationContext.getBean("paymentServiceNew").toString());
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
