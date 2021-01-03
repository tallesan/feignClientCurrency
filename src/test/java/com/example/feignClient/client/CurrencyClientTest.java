package com.example.feignClient.client;

import static com.github.tomakehurst.wiremock.client.WireMock.anyUrl;

import com.example.feignClient.model.CurrencyResponse;
import com.example.feignClient.model.ParamQuery;
import com.example.feignClient.service.ServiceClientImpl;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.common.ConsoleNotifier;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import com.github.tomakehurst.wiremock.extension.responsetemplating.ResponseTemplateTransformer;
import com.github.tomakehurst.wiremock.junit.WireMockRule;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
  private CurrencyClient currencyClient;
  @Autowired
  private ParamQuery paramQuery;
  @Autowired
  private ServiceClientImpl serviceClient;

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
    CurrencyResponse currencyResponse = currencyClient.getCurrency(paramQuery.getAppId(),
        paramQuery.getBase());
    Assertions.assertThat(currencyResponse.getRates().get("USD")).isEqualTo(1);
    Assertions.assertThat(currencyResponse.getRates().size()).isEqualTo(171);
    Assertions.assertThat(currencyResponse.getRates().get("RUB")).isEqualTo(74.145);
    Assertions.assertThat(currencyResponse.getRates().get("GBP")).isEqualTo(0.749398);
    Assertions.assertThat(currencyResponse.getTimestamp()).isEqualTo(1608915600);
    Assertions.assertThat(currencyResponse.getLicense()).isEqualTo("https://openexchangerates.org/license");
    Assertions.assertThat(currencyResponse.getDisclaimer()).isEqualTo("Usage subject to terms: https://openexchangerates.org/terms");
  }

  @Test
  public void historyDayTest() {
    CurrencyResponse currencyResponse = currencyClient
        .getCurrencyHistory("2020-12-18", paramQuery.getAppId(), paramQuery.getBase());
    Assertions.assertThat(currencyResponse.getRates().get("USD")).isEqualTo(1);
    Assertions.assertThat(currencyResponse.getRates().size()).isEqualTo(171);
    Assertions.assertThat(currencyResponse.getRates().get("RUB")).isEqualTo(74.145);
    Assertions.assertThat(currencyResponse.getRates().get("GBP")).isEqualTo(0.749398);
    Assertions.assertThat(currencyResponse.getTimestamp()).isEqualTo(1608915600);
  }
  @Test
  public void paramTest(){
    Assertions.assertThat(paramQuery.getAppId()).isEqualTo("1111");
    Assertions.assertThat(paramQuery.getBase()).isEqualTo("USD");
    Assertions.assertThat(paramQuery.getAlternative()).isEqualTo("RUB");
    Assertions.assertThat(paramQuery.getOpponent()).isEqualTo("EUR");
    Assertions.assertThat(paramQuery.getDaysAgo()).isEqualTo(1);
  }
  @Test
  public void testThisDay(){
    LocalDateTime thisDay = LocalDateTime.now();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    CurrencyResponse currencyResponse = serviceClient.getThisDay();
    Assertions.assertThat(currencyResponse.getRates().get("USD")).isEqualTo(1);
    Assertions.assertThat(currencyResponse.getRates().get("RUB")).isEqualTo(74.145);
    Assertions.assertThat(currencyResponse.getDateCurrency().format(formatter)).isEqualTo(thisDay.format(formatter));
    Assertions.assertThat(currencyResponse.getOpponent()).isEqualTo("EUR");
  }

  @Test
  public void testHistoryDay(){
    LocalDateTime historyDay = LocalDateTime.now().minusDays(paramQuery.getDaysAgo());
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    CurrencyResponse currencyResponse = serviceClient.getHistoryDay();
    Assertions.assertThat(currencyResponse.getRates().get("USD")).isEqualTo(1);
    Assertions.assertThat(currencyResponse.getRates().get("RUB")).isEqualTo(74.145);
    Assertions.assertThat(currencyResponse.getDateCurrency().format(formatter)).isEqualTo(historyDay.format(formatter));
  }

}
