package ru.bmstu.hadoop.labs;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.japi.pf.ReceiveBuilder;

import java.util.ArrayList;
import java.util.Random;

public class StoreActor extends AbstractActor {
    private final ArrayList<String> serversList = new ArrayList<>();
    private final Random random = new Random();
    private final String self;

    @Override
    public Receive createReceive() {
        return ReceiveBuilder.create()
                .match(GetServer.class, this::getRandomServer)
                .match(PutServers.class, this::putServers)
                .build();
    }

    private void getRandomServer(GetServer get) {
        int randomServer = random.nextInt(serversList.size());
        sender().tell(serversList.get(randomServer), ActorRef.noSender());
    }

    private void putServers(PutServers list) {
        serversList.addAll(list.getServersList());
    }
}
