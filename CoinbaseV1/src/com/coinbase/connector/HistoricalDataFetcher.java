package com.coinbase.connector;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class HistoricalDataFetcher {
    
    private static final String BASE_URL = "https://api.pro.coinbase.com/products";
    private static final String GRANULARITY = "86400"; // Данные за одни сутки

    public String fetchMonthlyData(String currencyPair, LocalDate start, LocalDate end) {
        String responseBody = "";

        // Проверка и корректировка временного диапазона
        long daysBetween = ChronoUnit.DAYS.between(start, end);
        if (daysBetween > 300) {
            end = start.plusDays(300); // Корректировка конечной даты
            System.out.println("Конечная дата была скорректирована для соблюдения ограничения в 300 дней.");
        }

        try {
            // Форматируем даты в ISO_INSTANT формат.
            DateTimeFormatter formatter = DateTimeFormatter.ISO_INSTANT;
            String startDateTime = start.atStartOfDay(ZoneOffset.UTC).toInstant().toString();
            String endDateTime = end.atStartOfDay(ZoneOffset.UTC).toInstant().toString();

            // Создаем URL запроса.
            String requestURL = String.format("%s/%s/candles?start=%s&end=%s&granularity=%s",
                                              BASE_URL, currencyPair, startDateTime, endDateTime, GRANULARITY);
            
            // Создаем и выполняем HTTP запрос.
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                                    .uri(URI.create(requestURL))
                                    .build();
            
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            
            // Возвращаем тело ответа как строку.
            responseBody = response.body();
        } catch (IOException e) {
            System.err.println("IO error during HTTP request: " + e.getMessage());
        } catch (InterruptedException e) {
            System.err.println("HTTP request interrupted: " + e.getMessage());
            Thread.currentThread().interrupt();
        } catch (Exception e) {
            System.err.println("Unexpected error during data fetch: " + e.getMessage());
        }
        return responseBody;
    }
}
