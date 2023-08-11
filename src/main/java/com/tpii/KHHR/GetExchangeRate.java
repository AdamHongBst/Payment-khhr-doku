package com.tpii.KHHR;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class GetExchangeRate {

    public static void main(String[] args) {
        String endpoint = "https://sandbox.lightspeedtransfer.com/gateway/v1.0/exchangeRate/get";
        String timestamp = "2023-03-10T14:46:41+07:00";
        String authorizationToken = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJjbGllbnRJZCI6Ijc2MjZlNTRlZmM0YTRmNWViODg1NDcyZGMwNDA4N2U2IiwiZXhwIjoxNjc4NDM1MjY5LCJ0aW1lc3RhbXAiOiIyMDIzLTAzLTEwVDE0OjQ2OjAzKzA3OjAwIn0.cp-mdhFKyMR_kjZSDjreD5fjDfnXlxczzEuSpPIsXhk";
        String signature = "x41I21AGE2kCAlc/YBxV7jJ3Pc+ROxu+KeC45HC9F0euqnB6sb58evdD5j0+bcnHxnDlkdmuQDlzKceemQ+Miw==";

        try {
            URL url = new URL(endpoint);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("X-TIMESTAMP", timestamp);
            connection.setRequestProperty("Authorization", authorizationToken);
            connection.setRequestProperty("X-SIGNATURE", signature);

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line;
                StringBuilder response = new StringBuilder();
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();

                // Parse JSON using Jackson
                ObjectMapper objectMapper = new ObjectMapper();
                ExchangeRateData exchangeRateData = objectMapper.readValue(response.toString(), ExchangeRateData.class);

                // Access the parsed data
                System.out.println("Response Code: " + exchangeRateData.getResponseCode());
                System.out.println("Response Message: " + exchangeRateData.getResponseMessage());

                // Access the list of exchange rates
                for (ExchangeRateItem item : exchangeRateData.getList()) {
                    System.out.println("Currency From: " + item.getCurrencyCodeFrom());
                    System.out.println("Currency To: " + item.getCurrencyCodeTo());
                    System.out.println("Exchange Rate: " + item.getExchangeRate());
                    System.out.println("Effective Time: " + item.getEffectiveTime());
                }
            } else {
                System.out.println("Error response code: " + responseCode);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
