package org.example.first.service;

import com.sun.net.httpserver.HttpServer;
import org.example.first.handler.MyHandler;

import java.net.InetSocketAddress;

public class SimpleHttpServer {
    public static void main(String[] args) throws Exception {
        HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);
        server.createContext("/first/main", new MyHandler());
        server.setExecutor(null); // creates a default executor
        server.start();

        // http://localhost:8000/first/main
    }
}