package ru.bmstu.hadoop.labs.messages;

import java.util.List;

public class PutServers {
    private final List<String> serversList;

    public List<String> getServersList() {
        return serversList;
    }

    public PutServers(List<String> serversList) {
        this.serversList = serversList;
    }
}
