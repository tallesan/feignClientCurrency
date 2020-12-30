package com.example.feignClient.client;

import com.example.feignClient.model.DataImg;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * Клиент для получения JSON объекта Gif-ки
 */
@FeignClient(url = "${urlImg}", name = "GiphyClient")
public interface GiphyClient {

  /**
   * Передаем ключ и Тэг
   */
  @GetMapping(value = "/random?api_key={apiKey}&tag={rich}&rating=r")
  DataImg getGiphyRich(@PathVariable("apiKey") String apiKey, @PathVariable("rich") String rich);

}
