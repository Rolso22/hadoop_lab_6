package ru.bmstu.hadoop.labs;

import akka.actor.ActorRef;
import org.apache.zookeeper.ZooKeeper;

import java.nio.charset.StandardCharsets;

import static ru.bmstu.hadoop.labs.Constants.*;

public class ZooServer {
    private String path;
    private String host;
    private int port;
    private ZooKeeper zoo;
    private ActorRef storeActor;

    public ZooServer(String path, String host, int port, ZooKeeper zoo, ActorRef storeActor) {
        this.path = path;
        this.host = host;
        this.port = port;
        this.zoo = zoo;
        this.storeActor = storeActor;
        start();
    }

    private void start() {
        zoo.create(path + SLASH + host + port, (host + COLON + port).getBytes(StandardCharsets.UTF_8), )
    }

}
