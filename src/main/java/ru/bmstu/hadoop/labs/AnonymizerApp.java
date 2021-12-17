package ru.bmstu.hadoop.labs;

import java.io.IOException;

public class AnonymizerApp {
    public static void main(String[] args) throws IOException, InterruptedException {

        if (args.length < 2) {
            System.out.println("need more arguments");
            System.exit(1);
        }
        String host = args[0];
        int port = Integer.parseInt(args[1]);
        HttpServer httpServer = new HttpServer(host, port);
        httpServer.start();
        System.in.read();
        httpServer.end();
    }
}
