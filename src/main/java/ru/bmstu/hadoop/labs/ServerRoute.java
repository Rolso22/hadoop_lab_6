package ru.bmstu.hadoop.labs;

import akka.NotUsed;
import akka.http.javadsl.model.HttpRequest;
import akka.http.javadsl.model.HttpResponse;
import akka.http.javadsl.server.Route;
import akka.stream.javadsl.Flow;

public class ServerRoute {


    public Flow<HttpRequest, HttpResponse, NotUsed> createFlow() {

    }
}
