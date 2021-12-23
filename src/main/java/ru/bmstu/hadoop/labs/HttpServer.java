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
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;
import java.util.concurrent.CompletionStage;

import static ru.bmstu.hadoop.labs.Constants.*;

public class HttpServer {
    private final String host;
    private final int port;
    private final ActorSystem system;
    private CompletionStage<ServerBinding> binding;

    public HttpServer(String host, int port) {
        this.host = host;
        this.port = port;
        system = ActorSystem.create("HttpServer");
    }

    public void start() throws IOException, InterruptedException, KeeperException {
        final Http http = Http.get(system);
        final ActorMaterializer materializer = ActorMaterializer.create(system);
        ActorRef storeActor = system.actorOf(Props.create(StoreActor.class));

        ZooServer zooServer = new ZooServer(SERVER_PATH, host, port, storeActor);
        zooServer.start();

        ServerRoute serverRoute = new ServerRoute(storeActor, host + COLON + port);
        final Flow<HttpRequest, HttpResponse, NotUsed> routeFlow = serverRoute.createRoute().flow(system, materializer);
        binding = http.bindAndHandle(
                routeFlow,
                ConnectHttp.toHost(host, port),
                materializer
        );
        System.out.println(SERVER_ONLINE + port);
    }

    public void end() {
        binding.thenCompose(ServerBinding::unbind)
                .thenAccept(unbound -> system.terminate());
    }
}
