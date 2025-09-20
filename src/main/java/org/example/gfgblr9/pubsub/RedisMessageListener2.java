package org.example.gfgblr9.pubsub;

import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;

public class RedisMessageListener2 implements MessageListener {
    @Override
    public void onMessage(Message message, byte[] pattern) {
        System.out.println("recieved:"+ new String(message.getBody())+" "+Thread.currentThread().getName() + " RedisMessageListener2");
    }
}
