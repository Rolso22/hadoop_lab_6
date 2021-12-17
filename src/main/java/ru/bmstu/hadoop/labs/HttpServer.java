package ru.bmstu.hadoop.labs;

import akka.NotUsed;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.http.javadsl.ConnectHttp;
import akka.http.javadsl.Http;
import akka.http.javadsl.ServerBinding;
import akka.http.javadsl.model.HttpRequest;
import akka.http.javadsl.model.HttpResponse;
import akka.stream.ActorMaterializer;
import akka.stream.javadsl.Flow;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;
import java.util.concurrent.CompletionStage;

import static ru.bmstu.hadoop.labs.Constants.*;

public class HttpServer implements Watcher {
    private String host;
    private int port;
    private ActorSystem system;
    private ActorRef storeActor;
    private ZooKeeper zooKeeper;
    private CompletionStage<ServerBinding> binding;

    public HttpServer(String host, int port) {
        this.host = host;
        this.port = port;
        system = ActorSystem.create("HttpServer");
    }

    public void start() throws IOException, InterruptedException, KeeperException {
        zooKeeper = new ZooKeeper(DEFAULT_CONNECTION_HOST, TIME_OUT_MILLIS, this);

        final Http http = Http.get(system);
        final ActorMaterializer materializer = ActorMaterializer.create(system);
        storeActor = system.actorOf(Props.create(StoreActor.class));

        ZooServer zooServer = new ZooServer(SERVER_PATH, host, port, zooKeeper, storeActor);
        zooServer.start();

        ServerRoute serverRoute = new ServerRoute(storeActor);
        final Flow<HttpRequest, HttpResponse, NotUsed> routeFlow = serverRoute.createRoute().flow(system, materializer);
        binding = http.bindAndHandle(
                routeFlow,
                ConnectHttp.toHost(DEFAULT_HOST, DEFAULT_PORT),
                materializer
        );
        System.out.println(SERVER_ONLINE + DEFAULT_PORT);
    }

    public void end() throws InterruptedException {
        zooKeeper.close();
        binding.thenCompose(ServerBinding::unbind)
                .thenAccept(unbound -> system.terminate());
    }

    @Override
    public void process(WatchedEvent watchedEvent) {
        System.out.println(watchedEvent.toString());
    }
}
