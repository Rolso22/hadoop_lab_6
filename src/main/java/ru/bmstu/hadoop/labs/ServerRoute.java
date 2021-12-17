package ru.bmstu.hadoop.labs;

import akka.http.javadsl.server.Route;

import static akka.http.javadsl.server.Directives.*;

public class ServerRoute {


    public Route createRoute() {
        return route(
                get(() -> parameter("", (url) -> {

                })))
    }
}
