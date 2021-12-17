package ru.bmstu.hadoop.labs;

import akka.http.javadsl.server.Route;

import static akka.http.javadsl.server.Directives.*;

import static ru.bmstu.hadoop.labs.Constants.*;

public class ServerRoute {


    public Route createRoute() {
        return route(
                get(() -> parameter(URL, (url) ->
                        parameter()

                )))
    }
}
