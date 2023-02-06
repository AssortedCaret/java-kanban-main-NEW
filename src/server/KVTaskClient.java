package server;

import com.google.gson.*;

import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class KVTaskClient {
    private static String url;
    private String apiToken = "";
    private static final Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;

    KVTaskClient(String newUrl) throws IOException, InterruptedException {
        this.url = newUrl;
        URI uri = URI.create(url + "/register");
        // создаем объект, описывающий HTTP-запрос
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(uri)
                .build();
        HttpClient client = HttpClient.newHttpClient();
        // получаем стандартный обработчик тела запроса
        // с конвертацией содержимого в строку
        HttpResponse.BodyHandler<String> handler = HttpResponse.BodyHandlers.ofString();
        HttpResponse<String> response = client.send(request, handler);
        apiToken = response.body();
    }

    public void put(String key, String json) {
        HttpClient client = HttpClient.newHttpClient();
        URI urlU = URI.create(url + "/save/" + key + "?API_TOKEN=" + apiToken);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(urlU)
                .GET()
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString(DEFAULT_CHARSET));
            // проверяем, успешно ли обработан запрос
            if (response.statusCode() == 200) {
                System.out.println("Ответ от сервера соответствует ожидаемому.");
                return;
            } else
                System.out.println("Возникла следующая ошибка: " + response.body());
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public String load(String key) {
        String count = "";
        HttpClient client = HttpClient.newHttpClient();
        URI urlU = URI.create(url + "/load/" + key);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(urlU)
                .GET()
                .build();
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            // проверяем, успешно ли обработан запрос
            if (response.statusCode() == 200) {
                JsonElement jsonElement = JsonParser.parseString(response.body());
                if (!jsonElement.isJsonArray()) { // проверяем, точно ли мы получили JSON-массив
                    System.out.println("Ответ от сервера не соответствует ожидаемому.");
                }
                // получаем значение и записываем в переменные
                JsonArray jsonArray = jsonElement.getAsJsonArray();
                count = String.valueOf(JsonParser.parseString(response.body()).getAsJsonArray());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return count;
    }
}