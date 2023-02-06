package server.handlers;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import manager.*;
import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

import task.Epic;

public class EpicHandler implements HttpHandler {
    private static final int PORT = 8080;
    private static final Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;
    private static final Gson gson = new Gson();
    String body;
    TaskManager taskManager;

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String endpoint = exchange.getRequestMethod();
        switch (endpoint) {
            case "GET": {
                getEpic(exchange);
                break;
            }
            case "DELETE": {
                deleteEpic(exchange);
                break;
            }
            case "POST": {
                putEpic(exchange);
                break;
            }
            default:
                writeResponse(exchange, "Такого эндпоинта не существует", 404);
        }
    }
    private void getEpic(HttpExchange exchange) throws IOException {
        if (exchange.getRequestURI().getQuery() == null) {
            body = gson.toJson(taskManager.getEpics());
            writeResponse(exchange, body, 200);
            return;
        }
        int id = getEpicId(exchange).get();
        if (taskManager.getEpicMap().containsKey(id)) {
            body = gson.toJson(taskManager.getEpicForID(id));
        } else {
            writeResponse(exchange, "Задач с таким id не найдено!", 404);
        }
        writeResponse(exchange, body, 200);
    }

    private void putEpic(HttpExchange exchange) throws IOException {
        try {
            InputStream json = exchange.getRequestBody();
            String jsonEpic = new String(json.readAllBytes(), DEFAULT_CHARSET);
            Epic epic = gson.fromJson(jsonEpic, Epic.class);
            if (epic == null) {
                writeResponse(exchange, "Задача не должна быть пустой!", 400);
                return;
            }
            if (epic.getId() != null && taskManager.getEpicMap().containsKey(epic.getId())) {
                taskManager.updateEpic(epic.getId() ,epic);
                writeResponse(exchange, "Такая задача существует и была обновлена", 218);
                return;
            }
            taskManager.putEpic(epic);
            writeResponse(exchange, "Задача добавлена!", 201);

        } catch (JsonSyntaxException e) {
            writeResponse(exchange, "Получен некорректный JSON", 400);
        }
    }

    private void deleteEpic(HttpExchange exchange) throws IOException {
        if (exchange.getRequestURI().getQuery() == null) {
            taskManager.deleteEpicMap();
            writeResponse(exchange, "Задачи удалены!", 200);
            return;
        }
        if (getEpicId(exchange).isEmpty()) {
            return;
        }
        int id = getEpicId(exchange).get();
        if (!taskManager.getEpicMap().containsKey(id)) {
            writeResponse(exchange, "Задач с таким id не найдено!", 404);
            return;
        }
        taskManager.deleteEpicMapID(id);
        writeResponse(exchange, "Задача удалена!", 200);
    }

    private Optional<Integer> getEpicId(HttpExchange exchange) {
        String[] pathParts = exchange.getRequestURI().getQuery().split("=");
        try {
            return Optional.of(Integer.parseInt(pathParts[1]));
        } catch (NumberFormatException exception) {
            return Optional.empty();
        }
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