package ru.bmstu.hadoop.labs;

import akka.actor.ActorRef;
import akka.http.javadsl.server.Route;
import akka.pattern.Patterns;
import org.asynchttpclient.AsyncHttpClient;
import org.asynchttpclient.Request;
import org.asynchttpclient.Response;

import java.time.Duration;
import java.util.concurrent.CompletionStage;

import static akka.http.javadsl.server.Directives.*;
import static org.asynchttpclient.Dsl.asyncHttpClient;

import static ru.bmstu.hadoop.labs.Constants.*;

public class ServerRoute {
    private final AsyncHttpClient httpClient = asyncHttpClient();
    private final ActorRef storeActor;
    private final String self_path;

    public ServerRoute(ActorRef storeActor, String self_path) {
        this.storeActor = storeActor;
        this.self_path = self_path;
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
        CompletionStage<Response> response;
        if (count > 0) {
            response = sendToServer(url, count - 1);
        } else {
            response = sendHttpRequest(httpClient.prepareGet(url).build());
        }
        return completeOKWithFutureString(response.thenApply(Response::getResponseBody));
    }

    private CompletionStage<Response> sendHttpRequest(Request path) {
        System.out.println(SEND_REQUEST + path.getUrl());
        return httpClient.executeRequest(path).toCompletableFuture();
    }

    private Request buildRequest(String path, String url, int count) {
        return httpClient.prepareGet(HTTP + path)
                .addQueryParam(URL, url)
                .addQueryParam(COUNT, String.valueOf(count))
                .build();
    }

    private CompletionStage<Response> sendToServer(String url, int count) {
        return Patterns.ask(storeActor, new GetRandomServer(self_path), Duration.ofMillis(TIME_OUT_MILLIS))
                .thenCompose(answer -> sendHttpRequest(buildRequest((String) answer, url, count)));
    }
}
