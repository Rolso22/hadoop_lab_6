package ru.bmstu.hadoop.labs;

import akka.http.javadsl.server.Route;
import org.asynchttpclient.AsyncHttpClient;
import org.asynchttpclient.Response;

import java.util.concurrent.CompletionStage;

import static akka.http.javadsl.server.Directives.*;
import static org.asynchttpclient.Dsl.asyncHttpClient;

import static ru.bmstu.hadoop.labs.Constants.*;

public class ServerRoute {
    private final AsyncHttpClient httpClient = asyncHttpClient();

    public Route createRoute() {
        return route(
                get(() -> parameter(URL, (url) ->
                        parameter(COUNT, (count) -> {
                            routeHandler(url, Integer.parseInt(count))
                        }))
                )
        );
    }

    private Route routeHandler(String url, int count) {
        CompletionStage<Response> response = null;
        if (count > 0) {

        } else {
            response = sendRequest(url);
        }
        return completeOKWithFuture(response.thenCompose(s -> s.getResponseBody()));
    }

    private CompletionStage<Response> sendRequest(String url) {
        return httpClient.prepareGet(url).execute().toCompletableFuture();
    }

}
