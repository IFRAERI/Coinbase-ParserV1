package com.coinbase.connector;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class CoinbaseAccountService {

    public void printAccountBalances(String jsonResponse) {
        try {
            JSONObject jsonObject = new JSONObject(jsonResponse);
            JSONArray accounts = jsonObject.getJSONArray("data");
            for (int i = 0; i < accounts.length(); i++) {
                JSONObject account = accounts.getJSONObject(i);
                String currency = account.getJSONObject("balance").getString("currency");
                String amount = account.getJSONObject("balance").getString("amount");
                System.out.println("Currency: " + currency + " - Balance: " + amount);
            }
        } catch (JSONException e) {
            System.err.println("An error occurred while parsing the account balances: " + e.getMessage());
        }
    }
}
