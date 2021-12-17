package ru.bmstu.hadoop.labs;

import org.apache.zookeeper.ZooKeeper;

import static ru.bmstu.hadoop.labs.Constants.*;

public class AnonymizerApp {
    public static void main(String[] args) {

        if (args.length < 2) {
            System.out.println("need more arguments");
            System.exit(1);
        }
        String host = args[0];
        int port = Integer.parseInt(args[1]);
        HttpServer httpServer = new HttpServer(host, port);
        httpServer.start();
    }
}
