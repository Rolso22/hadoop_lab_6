package ru.bmstu.hadoop.labs;

import akka.actor.AbstractActor;

import java.util.ArrayList;

public class StoreActor extends AbstractActor {
    private final ArrayList<String> serversList = new ArrayList<>();

    @Override
    public Receive createReceive() {
        return receiveBuilder().build()
                
    }
}
