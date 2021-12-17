package ru.bmstu.hadoop.labs;

import akka.http.javadsl.server.Route;
import org.asynchttpclient.AsyncHttpClient;

import javax.xml.ws.Response;
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
        CompletionStage<Response> response;
        if (count > 0) {

        } else {
            response = sendRequest(url);
        }
    }

    private CompletionStage<Response> sendRequest(String url) {
        httpClient.prepareGet(url).execute()
    }

}
