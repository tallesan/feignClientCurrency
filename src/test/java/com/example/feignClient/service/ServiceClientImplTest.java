package com.example.feignClient.service;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.feignClient.client.CurrencyClient;
import com.example.feignClient.model.CurrencyResponse;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest()
class ServiceClientImplTest {

  @Autowired
  ServiceClientImpl serviceClient;

  @Mock
  CurrencyClient currencyClient;

  public static Map<String, Double> currency;
  public static CurrencyResponse currencyResponse;
  public static Map<String, Double> currencyOpponent;

  @BeforeEach
  public void setup() {

    currency = new HashMap<>();
    currency.put("AED", 3.6729);
    currency.put("AFN", 77.050006);
    currency.put("EUR", 0.82);
    currency.put("RUB", 73.15);
    currency.put("USD", 1D);

    currencyOpponent = new HashMap<>();
    currencyOpponent.put("AED", 3.7729);
    currencyOpponent.put("AFN", 77.150006);
    currencyOpponent.put("EUR", 0.91);
    currencyOpponent.put("RUB", 75.15);
    currencyOpponent.put("USD", 1D);

    LocalDateTime.now();

    currencyResponse = new CurrencyResponse(LocalDateTime.now(),"EUR","disclaimer","license",111, "USD", currency);

  }

  @Test
  void testGetThisDay() {
    when(currencyClient.getUser(anyString(), anyString())).thenReturn(
        ServiceClientImplTest.currencyResponse);
    CurrencyResponse currencyResponse = currencyClient.getUser("1111", "USD");

    Assertions.assertThatObject(currencyResponse).isEqualTo(ServiceClientImplTest.currencyResponse);
    Assertions.assertThat(currencyResponse.getBase()).isEqualTo("USD");
    Assertions.assertThat(currencyResponse.getRates().size()).isEqualTo(5);
    Assertions.assertThat(currencyResponse.getRates().get("EUR")).isEqualTo(0.82);
    verify(currencyClient).getUser("1111", "USD");
  }

  @Test
  void testGetHistoryDay() {
    when(currencyClient.getUserHistory(anyString(), anyString(), anyString())).thenReturn(
        ServiceClientImplTest.currencyResponse);
    CurrencyResponse currencyResponse = currencyClient.getUserHistory("2020-12-19", "1111", "USD");
    Assertions.assertThat(currencyResponse.getRates().get("RUB")).isEqualTo(73.15);
    verify(currencyClient).getUserHistory("2020-12-19", "1111", "USD");
  }


  @Test
  void getCompareMoney() {
    boolean actualBol = serviceClient.getCompareMoney(currency, currencyOpponent);
    Assertions.assertThat(actualBol).isEqualTo(true);
  }

  @Test
  void changeMoney() {
    CurrencyResponse currencyResponse = serviceClient.changeMoney(ServiceClientImplTest.currencyResponse);
    Assertions.assertThat(currencyResponse.getRates().size()).isEqualTo(5);
    Assertions.assertThat(currencyResponse.getRates().get("USD")).isEqualTo(73.15);
  }
}