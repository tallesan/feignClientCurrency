package com.example.feignClient.client;

import static com.github.tomakehurst.wiremock.client.WireMock.anyUrl;

import com.example.feignClient.model.CurrencyResponse;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.common.ConsoleNotifier;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import com.github.tomakehurst.wiremock.extension.responsetemplating.ResponseTemplateTransformer;
import com.github.tomakehurst.wiremock.junit.WireMockRule;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest
public class CurrencyClientTest {

  @Autowired
  CurrencyClient currencyClient;

  @Rule
  public WireMockRule wireMockRule = new WireMockRule(WireMockConfiguration
      .options().port(8888)
      .notifier(new ConsoleNotifier(true))
      .extensions(new ResponseTemplateTransformer(true)));

  @Before
  public void setup() {
    wireMockRule.stubFor(WireMock.get(anyUrl())
        .willReturn(WireMock.aResponse()
        .withHeader("Content-Type", "application/json")
        .withBodyFile("userTest.json")
        ));
  }

  @Test
  public void shouldReturnUserResponseThisDay() {
    CurrencyResponse currencyResponse = currencyClient.getCurrency("1111", "USD");
    Assertions.assertThat(currencyResponse.getRates().get("USD")).isEqualTo(1);
    Assertions.assertThat(currencyResponse.getRates().size()).isEqualTo(171);
    Assertions.assertThat(currencyResponse.getRates().get("RUB")).isEqualTo(74.145);
    Assertions.assertThat(currencyResponse.getRates().get("GBP")).isEqualTo(0.749398);
    Assertions.assertThat(currencyResponse.getTimestamp()).isEqualTo(1608915600);
  }

  @Test
  public void historyDayTest() {
    CurrencyResponse currencyResponse = currencyClient.getCurrencyHistory("2020-12-18", "1111", "USD");
    Assertions.assertThat(currencyResponse.getRates().get("USD")).isEqualTo(1);
    Assertions.assertThat(currencyResponse.getRates().size()).isEqualTo(171);
    Assertions.assertThat(currencyResponse.getRates().get("RUB")).isEqualTo(74.145);
    Assertions.assertThat(currencyResponse.getRates().get("GBP")).isEqualTo(0.749398);
    Assertions.assertThat(currencyResponse.getTimestamp()).isEqualTo(1608915600);
  }


}
