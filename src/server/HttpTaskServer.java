package server;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpServer;
import manager.*;

import server.handlers.AllTaskHandler;
import server.handlers.HistoryHandler;
import server.handlers.TaskHandler;
import java.io.*;
import java.net.InetSocketAddress;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class HttpTaskServer {
    private static final int PORT = 8080;
    private static final Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;
    private static HttpServer httpServer;
    private static String url = "http://localhost:8080/tasks/";

    public static void main(String[] args) throws IOException, InterruptedException {
        httpServer = HttpServer.create();

        httpServer.bind(new InetSocketAddress(PORT), 0);
        httpServer.createContext(url + "GET/task?id=", new TaskHandler());
        httpServer.createContext(url + "GET/epic?id=", new TaskHandler());
        httpServer.createContext(url + "GET/subtask?id=", new TaskHandler());
        httpServer.createContext(url + "GET/history/", new HistoryHandler());
        httpServer.createContext(url + "GET/tasks", new AllTaskHandler());
    }

    public void start(){
        httpServer.start();
    }

    public void stop(){
        httpServer.stop(3000);
    }
}
