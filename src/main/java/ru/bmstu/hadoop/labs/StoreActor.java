package ru.bmstu.hadoop.labs;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.japi.pf.ReceiveBuilder;
import ru.bmstu.hadoop.labs.messages.GetRandomServer;
import ru.bmstu.hadoop.labs.messages.PutServers;

import java.util.ArrayList;
import java.util.Random;

public class StoreActor extends AbstractActor {
    private final ArrayList<String> serversList = new ArrayList<>();
    private final Random random = new Random();

    @Override
    public Receive createReceive() {
        return ReceiveBuilder.create()
                .match(GetRandomServer.class, this::getRandomServer)
                .match(PutServers.class, this::putServers)
                .build();
    }

    private void getRandomServer(GetRandomServer get) {
        String self = get.getSelf();
        int randomServer = random.nextInt(serversList.size());
        while (serversList.get(randomServer).equals(self)) {
            randomServer = random.nextInt(serversList.size());
        }
        sender().tell(serversList.get(randomServer), ActorRef.noSender());
    }

    private void putServers(PutServers list) {
        serversList.clear();
        serversList.addAll(list.getServersList());
    }
}
