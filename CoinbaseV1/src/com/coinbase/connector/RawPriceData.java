package com.coinbase.connector;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.List;
import java.util.ArrayList;


public class RawPriceData {
	 private double price;
	    private String currency;
	    private long timestamp;
	    
	    public List<RawPriceData> parseJsonToRawPriceData(String jsonData) {
	        List<RawPriceData> rawPriceDataList = new ArrayList<>();
	        JSONArray jsonArray = new JSONArray(jsonData);

	        for (int i = 0; i < jsonArray.length(); i++) {
	            JSONObject jsonObject = jsonArray.getJSONObject(i);
	            
	            // Предполагается, что JSON объект содержит ключи "price", "currency" и "timestamp"
	            double price = jsonObject.getDouble("price");
	            String currency = jsonObject.getString("currency");
	            long timestamp = jsonObject.getLong("timestamp");

	            rawPriceDataList.add(new RawPriceData(price, currency, timestamp));
	        }

	        return rawPriceDataList;
	    }


	    // Конструктор
	    public RawPriceData(double price, String currency, long timestamp) {
	        this.price = price;
	        this.currency = currency;
	        this.timestamp = timestamp;
	    }

	    // Геттеры и сеттеры
	    public double getPrice() {
	        return price;
	    }

	    public void setPrice(double price) {
	        this.price = price;
	    }

	    public String getCurrency() {
	        return currency;
	    }

	    public void setCurrency(String currency) {
	        this.currency = currency;
	    }

	    public long getTimestamp() {
	        return timestamp;
	    }

	    public void setTimestamp(long timestamp) {
	        this.timestamp = timestamp;
	    }

}
