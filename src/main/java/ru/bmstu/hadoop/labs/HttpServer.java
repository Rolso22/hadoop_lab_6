package ru.bmstu.hadoop.labs;

import akka.actor.ActorSystem;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;

import static ru.bmstu.hadoop.labs.Constants.DEFAULT_CONNECTION_HOST;
import static ru.bmstu.hadoop.labs.Constants.TIME_OUT_MILLIS;

public class HttpServer implements Watcher {
    private String host;
    private int port;
    private ActorSystem system;

    public HttpServer(String host, int port) {
        this.host = host;
        this.port = port;
        system = ActorSystem.create("HttpServer");
    }

    public void start() throws IOException {
        ZooKeeper zooKeeper = new ZooKeeper(DEFAULT_CONNECTION_HOST, TIME_OUT_MILLIS, this);
        
    }

    @Override
    public void process(WatchedEvent watchedEvent) {
        System.out.println(watchedEvent.toString());
    }
}
