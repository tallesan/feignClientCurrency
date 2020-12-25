package com.example.feignClient.service;


import com.example.feignClient.model.UserResponse;
import java.util.Map;

public interface ServiceClient {

  UserResponse getThisDay();

  UserResponse getHistoryDay();

  boolean getCompareMoney(Map<String, Double> changeCurrency,
      Map<String, Double> changeCurrencyHistory);

  UserResponse changeMoney(UserResponse userResponse);
}
