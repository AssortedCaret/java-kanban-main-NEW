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

import task.*;

public class SubTaskHandler implements HttpHandler {
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
                getSubTask(exchange);
                break;
            }
            case "DELETE": {
                deleteSubTask(exchange);
                break;
            }
            case "POST": {
                putSubTask(exchange);
                break;
            }
            default:
                writeResponse(exchange, "Такого эндпоинта не существует", 404);
        }
    }
    private void getSubTask(HttpExchange exchange) throws IOException {
        if (exchange.getRequestURI().getQuery() == null) {
            body = gson.toJson(taskManager.getSubTasks());
            writeResponse(exchange, body, 200);
            return;
        }
        int id = getSubTaskId(exchange).get();
        if (taskManager.getSubTaskMap().containsKey(id)) {
            body = gson.toJson(taskManager.getSubTaskForID(id));
        } else {
            writeResponse(exchange, "Задач с таким id не найдено!", 404);
        }
        writeResponse(exchange, body, 200);
    }

    private void putSubTask(HttpExchange exchange) throws IOException {
        try {
            InputStream json = exchange.getRequestBody();
            String jsonSubTask = new String(json.readAllBytes(), DEFAULT_CHARSET);
            SubTask SubTask = gson.fromJson(jsonSubTask, SubTask.class);
            if (SubTask.getId() != null && taskManager.getSubTaskMap().containsKey(SubTask.getId())) {
                taskManager.updateSubTask(SubTask.getId() ,SubTask);
                writeResponse(exchange, "Такая задача существует и была обновлена", 218);
                return;
            }
            taskManager.putSubTask(SubTask, SubTask.getEpicId());
            writeResponse(exchange, "Задача успешно добавлена!", 201);

        } catch (JsonSyntaxException e) {
            writeResponse(exchange, "Получен некорректный JSON", 400);
        }
    }

    private void deleteSubTask(HttpExchange exchange) throws IOException {
        if (exchange.getRequestURI().getQuery() == null) {
            taskManager.deleteSubTaskMap();
            writeResponse(exchange, "Задачи удалены!", 200);
            return;
        }
        if (getSubTaskId(exchange).isEmpty()) {
            return;
        }
        int id = getSubTaskId(exchange).get();
        if (!taskManager.getSubTaskMap().containsKey(id)) {
            writeResponse(exchange, "Задач с таким id не найдено!", 404);
            return;
        }
        taskManager.deleteSubTaskMapID(id);
        writeResponse(exchange, "Задача удалена!", 200);
    }

    private Optional<Integer> getSubTaskId(HttpExchange exchange) {
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