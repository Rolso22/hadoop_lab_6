package ru.bmstu.hadoop.labs;

import akka.actor.ActorRef;
import org.apache.zookeeper.*;
import org.asynchttpclient.*;
import static org.asynchttpclient.Dsl.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

import static ru.bmstu.hadoop.labs.Constants.*;

public class ZooServer implements Watcher {
    private final String path;
    private final String host;
    private final int port;
    private ZooKeeper zoo;
    private final ActorRef storeActor;

    public ZooServer(String path, String host, int port, ActorRef storeActor) {
        this.path = path;
        this.host = host;
        this.port = port;
        this.storeActor = storeActor;
    }

    public void start() throws InterruptedException, KeeperException, IOException {
        zoo = new ZooKeeper(DEFAULT_CONNECTION_HOST, TIME_OUT_MILLIS, this);

        zoo.create(path + SLASH + host + COLON + port,
                (host + COLON + port).getBytes(StandardCharsets.UTF_8),
                ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
        List<String> serversList = zoo.getChildren(path, this);
        storeActor.tell(new PutServers(serversList), ActorRef.noSender());
    }

    @Override
    public void process(WatchedEvent watchedEvent) {
        List<String> serversList = null;
        try {
            serversList = zoo.getChildren(path, false);
        } catch (KeeperException | InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("serversList: " + serversList);
        storeActor.tell(new PutServers(serversList), ActorRef.noSender());
    }
}
