package com.coinbase.connector;

public class VolumeData {
	private double volume;
    private String currencyPair;
    private long timestamp;

    // Конструктор
    public VolumeData(double volume, String currencyPair, long timestamp) {
        this.volume = volume;
        this.currencyPair = currencyPair;
        this.timestamp = timestamp;
    }

    // Геттеры и сеттеры
    public double getVolume() {
        return volume;
    }

    public void setVolume(double volume) {
        this.volume = volume;
    }

    public String getCurrencyPair() {
        return currencyPair;
    }

    public void setCurrencyPair(String currencyPair) {
        this.currencyPair = currencyPair;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

}
