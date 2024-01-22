package com.coinbase.connector;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

import com.coinbase.connector.DataProcessor.PriceData;

public class Application {
    private CoinbaseConnector coinbaseConnector;
    private GoogleDriveConnector driveService;
    private HistoricalDataFetcher dataFetcher;
    private DataProcessor dataProcessor;

    public void initialize() throws GeneralSecurityException, IOException {
        coinbaseConnector = CoinbaseConnector.getInstance();
        driveService = new GoogleDriveConnector();
        dataFetcher = new HistoricalDataFetcher();
        dataProcessor = new DataProcessor();

        driveService.setupDriveConnection();
    }

    public void start() {
        LocalDate startDate = LocalDate.of(2014, 1, 1);
        LocalDate endDate = LocalDate.now();
        long totalDays = ChronoUnit.DAYS.between(startDate, endDate); // Общее количество дней для обработки

        while (!startDate.isAfter(endDate)) { // Цикл продолжается пока начальная дата не позже конечной
            LocalDate currentEndDate = startDate; // Обработка данных за один день

            try {
                // Получение и обработка данных по каждой валютной паре
                processCurrencyPair("BTC-USD", startDate, currentEndDate);
                processCurrencyPair("ETH-USD", startDate, currentEndDate);
                processCurrencyPair("ETH-BTC", startDate, currentEndDate);

                // Вывод прогресса
                long processedDays = ChronoUnit.DAYS.between(LocalDate.of(2014, 1, 1), currentEndDate);
                double progress = (double) processedDays / totalDays * 100;
                System.out.printf("Прогресс обработки: %.2f%%\n", progress);

                // Обновление даты начала для следующего дня
                startDate = startDate.plusDays(1); // Сдвигаем на следующий день

                Thread.sleep(1000); // Пауза между запросами
            } catch (Exception e) {
                System.err.println("Ошибка в процессе обработки данных: " + e.getMessage());
                // Обработка исключений и возможный выход из цикла
                break;
            }
        }

        System.out.println("Обработка данных завершена.");
    }




    private void processCurrencyPair(String currencyPair, LocalDate startDate, LocalDate endDate) throws IOException {
        String currencyData = dataFetcher.fetchMonthlyData(currencyPair, startDate, endDate);
        List<RawPriceData> rawCurrencyData = dataProcessor.parseJsonToRawPriceData(currencyData);
        List<PriceData> processedCurrencyData = dataProcessor.processPriceData(rawCurrencyData);

        for (PriceData data : processedCurrencyData) {
            String dataJson = dataProcessor.convertPriceDataToJson(data);
            // Предполагаем, что fileName формируется как currencyPair + "-data.json"
            driveService.saveDataToDrive(currencyPair + "-data.json", dataJson);
        }
    }


    // ... другие методы класса ...ыск
}
