package com.example.feignClient.model;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Ключ для получения Gif. Параметры берутся из application.yaml
 */
@Data
@Component
public class GiphyParam {

  @Value("${paramImg.apiKey}")
  String apiKey;
}
