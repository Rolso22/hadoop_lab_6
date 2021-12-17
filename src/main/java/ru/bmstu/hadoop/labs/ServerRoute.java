package ru.bmstu.hadoop.labs;

import akka.NotUsed;
import akka.http.javadsl.model.HttpRequest;
import akka.http.javadsl.model.HttpResponse;
import akka.http.javadsl.server.Route;
import akka.stream.javadsl.Flow;

import static akka.http.javadsl.server.Directives.route;

public class ServerRoute {


    public Route createRoute() {
        return route(
                get())
    }
}
