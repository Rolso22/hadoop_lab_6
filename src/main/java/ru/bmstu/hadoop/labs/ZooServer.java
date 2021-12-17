package ru.bmstu.hadoop.labs;

public class ZooServer {
    private String path;
    private String host;
    private int port;

    public ZooServer(String path, String host, int port) {
        this.path = path;
        this.host = host;
        this.port = port;
        start();
    }

    private void start() {
        
    }

}
