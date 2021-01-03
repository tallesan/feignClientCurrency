package com.example.feignClient.service;

import com.example.feignClient.client.CurrencyClient;
import com.example.feignClient.model.CurrencyResponse;
import com.example.feignClient.model.ParamQuery;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ServiceClientImpl implements ServiceClient {

  public static final LocalDateTime CURRENT_DATE = LocalDateTime.now();

  private final ParamQuery paramQuery;
  private final CurrencyClient client;

  @Autowired
  public ServiceClientImpl(CurrencyClient client, ParamQuery paramQuery) {
    this.client = client;
    this.paramQuery = paramQuery;
  }

  /**
   * получаем текущие курсы
   */
  @Override
  public CurrencyResponse getThisDay() {

    CurrencyResponse currencyResponse =
        client.getCurrency(paramQuery.getAppId(), paramQuery.getBase());
    currencyResponse.setOpponent(paramQuery.getOpponent());
    currencyResponse.setDateCurrency(CURRENT_DATE);
    return currencyResponse;
  }

  /**
   * получаем курсы предыдущего дня. Можно указывать сколько дней назад
   */
  @Override
  public CurrencyResponse getHistoryDay() {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    String date = formatter.format(CURRENT_DATE.minusDays(paramQuery.getDaysAgo()));
    CurrencyResponse currencyResponse = client
        .getCurrencyHistory(date, paramQuery.getAppId(), paramQuery.getBase());
    currencyResponse.setOpponent(paramQuery.getOpponent());
    currencyResponse.setDateCurrency(CURRENT_DATE.minusDays(paramQuery.getDaysAgo()));
    return currencyResponse;
  }

  /**
   * сравниваем курсы
   */
  @Override
  public boolean getCompareMoney(Map<String, Double> changeCurrency,
      Map<String, Double> changeCurrencyHistory) {
    double thisDay = changeCurrency.get(paramQuery.getOpponent());
    double historyDay = changeCurrencyHistory.get(paramQuery.getOpponent());
    if (historyDay < 1) {
      return thisDay < historyDay;
    }
    return thisDay >= historyDay;
  }

  /**
   * конвертируем в В альтернативную валюту
   */
  @Override
  public CurrencyResponse changeMoney(CurrencyResponse currencyResponse) {
    Double alternative = currencyResponse.getRates().get(paramQuery.getAlternative());
    currencyResponse.setBase(paramQuery.getAlternative());
    for (String search : currencyResponse.getRates().keySet()) {
      Double tmp = currencyResponse.getRates().get(search);
      currencyResponse.getRates().put(search, alternative / tmp);
    }
    return currencyResponse;
  }

}
