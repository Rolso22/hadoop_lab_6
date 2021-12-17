package ru.bmstu.hadoop.labs;

import akka.http.javadsl.server.Route;

import javax.xml.ws.Response;
import java.util.concurrent.CompletionStage;

import static akka.http.javadsl.server.Directives.*;

import static ru.bmstu.hadoop.labs.Constants.*;

public class ServerRoute {


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
        CompletionStage<Response> responce;
        if (count > 0) {
            
        }
    }

}
