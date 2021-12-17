package ru.bmstu.hadoop.labs;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.http.javadsl.Http;
import akka.stream.ActorMaterializer;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;

import static ru.bmstu.hadoop.labs.Constants.*;

public class HttpServer implements Watcher {
    private String host;
    private int port;
    private ActorSystem system;
    private ActorRef storeActor;
    private ZooKeeper zooKeeper;

    public HttpServer(String host, int port) {
        this.host = host;
        this.port = port;
        system = ActorSystem.create("HttpServer");
    }

    public void start() throws IOException {
        zooKeeper = new ZooKeeper(DEFAULT_CONNECTION_HOST, TIME_OUT_MILLIS, this);

        final Http http = Http.get(system);
        final ActorMaterializer materializer = ActorMaterializer.create(system);

    }

    public void end() throws InterruptedException {
        zooKeeper.close();
    }

    @Override
    public void process(WatchedEvent watchedEvent) {
        System.out.println(watchedEvent.toString());
    }
}
