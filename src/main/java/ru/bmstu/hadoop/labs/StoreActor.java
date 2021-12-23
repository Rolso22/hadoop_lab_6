package ru.bmstu.hadoop.labs;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.japi.pf.ReceiveBuilder;

import java.util.ArrayList;
import java.util.Random;

public class StoreActor extends AbstractActor {
    private final ArrayList<String> serversList = new ArrayList<>();
    private final Random random = new Random();

    @Override
    public Receive createReceive() {
        return ReceiveBuilder.create()
                .match(GetServer.class, this::getRandomServer)
                .match(PutServers.class, this::putServers)
                .build();
    }

    private void getRandomServer(GetServer get) {
        System.out.println("all servers: " + serversList);
        int randomServer = random.nextInt(serversList.size());
        System.out.println("server: " + serversList.get(randomServer));
        sender().tell(serversList.get(randomServer), ActorRef.noSender());
    }

    private void putServers(PutServers list) {
        //System.out.println("list: " + list.getServersList());
        serversList.addAll(list.getServersList());
    }

}
