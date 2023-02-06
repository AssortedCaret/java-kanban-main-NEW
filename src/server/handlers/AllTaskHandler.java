package server.handlers;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import manager.TaskManager;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class AllTaskHandler implements HttpHandler{
    private static final Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;
    private static final Gson gson = new Gson();
    String body;
    TaskManager taskManager;

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String endpoint = exchange.getRequestMethod();
        switch (endpoint) {
            case "GET": {
                getAllTasks(exchange);
                break;
            }
            default:
                writeResponse(exchange, "Такого эндпоинта не существует", 404);
        }
    }

    private void getAllTasks(HttpExchange exchange) throws IOException {
        body = gson.toJson(taskManager.getTaskMap());
        body += gson.toJson(taskManager.getEpicMap());
        body += gson.toJson(taskManager.getSubTaskMap());
        if (body.isEmpty()) {
            writeResponse(exchange, "Некорректный идентификатор!", 400);
        }
        else
            writeResponse(exchange, body, 200);
    }

    private void writeResponse(HttpExchange exchange,
                               String responseString,
                               int responseCode) throws IOException {
        if(responseString.isBlank()) {
            exchange.sendResponseHeaders(responseCode, 0);
        } else {
            byte[] bytes = responseString.getBytes(DEFAULT_CHARSET);
            exchange.sendResponseHeaders(responseCode, bytes.length);
            try (OutputStream os = exchange.getResponseBody()) {
                os.write(bytes);
            }
        }
        exchange.close();
    }
}
