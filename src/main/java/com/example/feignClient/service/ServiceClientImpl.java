package com.example.feignClient.service;


import com.example.feignClient.client.UserClient;
import com.example.feignClient.model.ParamQuery;
import com.example.feignClient.model.UserResponse;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ServiceClientImpl implements ServiceClient {

  private final ParamQuery paramQuery;
  private final UserClient client;

  @Autowired
  public ServiceClientImpl(UserClient client, ParamQuery paramQuery) {
    this.client = client;
    this.paramQuery = paramQuery;
  }
  /**
   * получаем текущие курсы
   */
  @Override
  public UserResponse getThisDay() {
    UserResponse userResponse =
        client.getUser(paramQuery.getAppId(), paramQuery.getBase());
    userResponse.setOpponent(paramQuery.getOpponent());
    return userResponse;
  }
  /**
   * получаем курсы предыдущего дня. Можно указывать сколько дней назад
   */
  @Override
  public UserResponse getHistoryDay() {
    LocalDateTime localDate = LocalDateTime.now().minusDays(1);
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    String date = formatter.format(localDate);

    UserResponse userResponse = client
        .getUserHistory(date, paramQuery.getAppId(), paramQuery.getBase());
    userResponse.setOpponent(paramQuery.getOpponent());
    return userResponse;
  }
  /**
   * сравниваем курсы
   */
  @Override
  public boolean getCompareMoney(Map<String, Double> changeCurrency,
      Map<String, Double> changeCurrencyHistory) {
    double thisDay = changeCurrency.get(paramQuery.getOpponent());
    double historyDay = changeCurrencyHistory.get(paramQuery.getOpponent());
    return thisDay >= historyDay;
  }
  /**
   * конвертируем в В альтернативную валюту
   */
  @Override
  public UserResponse changeMoney(UserResponse userResponse) {
    Double alternative = userResponse.getRates().get(paramQuery.getAlternative());
    userResponse.setBase(paramQuery.getAlternative());
    for (String search : userResponse.getRates().keySet()) {
      Double tmp = userResponse.getRates().get(search);
      userResponse.getRates().put(search, alternative / tmp);
    }
    userResponse.getRates().put(paramQuery.getBase(), 1 / alternative);
    userResponse.getRates().remove(paramQuery.getAlternative());

    return userResponse;
  }

}
