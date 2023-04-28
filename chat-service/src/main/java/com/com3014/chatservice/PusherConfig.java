package com.com3014.chatservice;

import com.pusher.rest.Pusher;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PusherConfig {

    @Value("${pusher.app_id}")
    public String appId;

    @Value("${pusher.key}")
    public String key;

    @Value("${pusher.secret}")
    public String secret;

    @Bean
    public Pusher pusher() {
        Pusher pusher = new Pusher(appId, key, secret);
        pusher.setCluster("eu");
        pusher.setEncrypted(true);
        return pusher;
    }
}
