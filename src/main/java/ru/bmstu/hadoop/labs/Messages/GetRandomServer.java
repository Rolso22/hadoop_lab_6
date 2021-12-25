package ru.bmstu.hadoop.labs.Messages;

public class GetRandomServer {
    private final String self;

    public GetRandomServer(String self) {
        this.self = self;
    }

    public String getSelf() {
        return self;
    }
}
