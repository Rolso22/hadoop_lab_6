package ru.bmstu.hadoop.labs;

import akka.actor.ActorRef;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.asynchttpclient.*;
import static org.asynchttpclient.Dsl.*;

import java.nio.charset.StandardCharsets;

import static ru.bmstu.hadoop.labs.Constants.*;

public class ZooServer {
    private final String path;
    private final String host;
    private final int port;
    private final ZooKeeper zoo;
    private ActorRef storeActor;

    public ZooServer(String path, String host, int port, ZooKeeper zoo, ActorRef storeActor) throws InterruptedException, KeeperException {
        this.path = path;
        this.host = host;
        this.port = port;
        this.zoo = zoo;
        this.storeActor = storeActor;
    }

    public void start() throws InterruptedException, KeeperException {
        zoo.create(path + SLASH + host + port,
                (host + COLON + port).getBytes(StandardCharsets.UTF_8),
                ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
    }

}
