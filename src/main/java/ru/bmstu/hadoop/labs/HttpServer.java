package ru.bmstu.hadoop.labs;

import akka.actor.ActorSystem;

public class HttpServer {
    private String host;
    private int port;
    private ActorSystem system;

    public HttpServer(String host, int port) {
        this.host = host;
        this.port = port;
        system = ActorSystem.create()
    }



}
