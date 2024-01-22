package com.coinbase.connector;

public class RawVolumeData {
	private double volume;
    private String currencyPair;
    private long timestamp;

    // Конструктор
    public RawVolumeData(double volume, String currencyPair, long timestamp) {
        this.volume = volume;
        this.currencyPair = currencyPair;
        this.timestamp = timestamp;
    }

    // Геттеры и сеттеры
    public double getVolume() {
        return volume;
    }

    public String getCurrencyPair() {
        return currencyPair;
    }

    public long getTimestamp() {
        return timestamp;
    }

}
