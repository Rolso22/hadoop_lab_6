package ru.bmstu.hadoop.labs;

import akka.actor.ActorRef;
import org.apache.zookeeper.ZooKeeper;

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
    }

    private void start() {

    }

}
