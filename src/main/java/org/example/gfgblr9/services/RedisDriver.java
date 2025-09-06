package org.example.gfgblr9.services;

public class RedisDriver {
    private String connectionId;
    private String hostname;
    private int port;

    public RedisDriver(String connectionId, String hostname, int port) {
        this.connectionId = connectionId;
        this.hostname = hostname;
        this.port = port;
    }
}
