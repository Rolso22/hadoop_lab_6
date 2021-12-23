package ru.bmstu.hadoop.labs;

import akka.actor.ActorRef;
import akka.http.javadsl.server.Route;
import akka.pattern.Patterns;
import org.asynchttpclient.AsyncHttpClient;
import org.asynchttpclient.Response;

import java.time.Duration;
import java.util.concurrent.CompletionStage;

import static akka.http.javadsl.server.Directives.*;
import static org.asynchttpclient.Dsl.asyncHttpClient;

import static ru.bmstu.hadoop.labs.Constants.*;

public class ServerRoute {
    private final AsyncHttpClient httpClient = asyncHttpClient();
    private ActorRef storeActor;

    public ServerRoute(ActorRef storeActor) {
        this.storeActor = storeActor;
    }

    public Route createRoute() {
        return route(
                get(() -> parameter(URL, (url) ->
                        parameter(COUNT, (count) ->
                            routeHandler(url, Integer.parseInt(count))
                        ))
                )
        );
    }

    private Route routeHandler(String url, int count) {
        System.out.println("url: " + url);
        CompletionStage<Response> response;
        if (count > 0) {
            response = sendToServer(url, count - 1);
        } else {
            response = sendRequest(url);
        }
        return completeOKWithFutureString(response.thenApply(Response::getResponseBody));
    }

    private CompletionStage<Response> sendRequest(String url) {
        //url = "http://" + url;
        System.out.println("send url: " + url);
        return httpClient.prepareGet(url).execute().toCompletableFuture();
    }

    private CompletionStage<Response> sendToServer(String url, int count) {
        return Patterns.ask(storeActor, new GetServer(), Duration.ofMillis(TIME_OUT_MILLIS))
                .thenCompose(answer -> sendRequest("http://" + "localhost:8071" + "/?url=" + url + "&" + "count=" + count));
    }
}
