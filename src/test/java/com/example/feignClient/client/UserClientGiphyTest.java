package com.example.feignClient.client;

import static com.github.tomakehurst.wiremock.client.WireMock.anyUrl;

import com.example.feignClient.model.DataImg;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.common.ConsoleNotifier;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import com.github.tomakehurst.wiremock.extension.responsetemplating.ResponseTemplateTransformer;
import com.github.tomakehurst.wiremock.junit.WireMockRule;
import org.assertj.core.api.Assertions;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserClientGiphyTest {

  @Autowired
  UserClientGiphy userClientGiphy;

  @Rule
  public WireMockRule wireMockRule = new WireMockRule(WireMockConfiguration
      .options().port(8888)
      .notifier(new ConsoleNotifier(true))
      .extensions(new ResponseTemplateTransformer(true)));

  @Test
  public void testGiphy() {
    wireMockRule.stubFor(WireMock.get(anyUrl())
        .willReturn(WireMock.aResponse()
        .withHeader("Content-Type", "application/json")
        .withBodyFile("testGiphi.json")
        ));
    DataImg dataImg = userClientGiphy.getGiphyRich("1111", "rich");
    Assertions.assertThat(dataImg.getAdditionalProperties()
        .get("data").getImageUrl())
        .isEqualTo("https://media1.giphy.com/media/UwPyIExTOTeoM/giphy.gif");
  }
}
