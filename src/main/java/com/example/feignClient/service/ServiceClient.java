package com.example.feignClient.service;


import com.example.feignClient.model.CurrencyResponse;
import java.util.Map;

public interface ServiceClient {

  CurrencyResponse getThisDay();

  CurrencyResponse getHistoryDay();

  boolean getCompareMoney(Map<String, Double> changeCurrency,
      Map<String, Double> changeCurrencyHistory);

  CurrencyResponse changeMoney(CurrencyResponse currencyResponse);
}
