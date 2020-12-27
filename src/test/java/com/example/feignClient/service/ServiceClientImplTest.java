package com.example.feignClient.service;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


import com.example.feignClient.client.UserClient;
import com.example.feignClient.model.UserResponse;
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
  UserClient userClient;

  public static Map<String, Double> currency;
  public static UserResponse userResponse;
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

    userResponse = new UserResponse(LocalDateTime.now(),"EUR","disclaimer","license",111, "USD", currency);

  }

  @Test
  void testGetThisDay() {
    when(userClient.getUser(anyString(), anyString())).thenReturn(
        ServiceClientImplTest.userResponse);
    UserResponse userResponse = userClient.getUser("1111", "USD");

    Assertions.assertThatObject(userResponse).isEqualTo(ServiceClientImplTest.userResponse);
    Assertions.assertThat(userResponse.getBase()).isEqualTo("USD");
    Assertions.assertThat(userResponse.getRates().size()).isEqualTo(5);
    Assertions.assertThat(userResponse.getRates().get("EUR")).isEqualTo(0.82);
    verify(userClient).getUser("1111", "USD");
  }

  @Test
  void testGetHistoryDay() {
    when(userClient.getUserHistory(anyString(), anyString(), anyString())).thenReturn(
        ServiceClientImplTest.userResponse);
    UserResponse userResponse = userClient.getUserHistory("2020-12-19", "1111", "USD");
    Assertions.assertThat(userResponse.getRates().get("RUB")).isEqualTo(73.15);
    verify(userClient).getUserHistory("2020-12-19", "1111", "USD");
  }


  @Test
  void getCompareMoney() {
    boolean actualBol = serviceClient.getCompareMoney(currency, currencyOpponent);
    Assertions.assertThat(actualBol).isEqualTo(false);
  }

  @Test
  void changeMoney() {
    UserResponse userResponse = serviceClient.changeMoney(ServiceClientImplTest.userResponse);
    Assertions.assertThat(userResponse.getRates().size()).isEqualTo(5);
    Assertions.assertThat(userResponse.getRates().get("USD")).isEqualTo(73.15);
  }
}