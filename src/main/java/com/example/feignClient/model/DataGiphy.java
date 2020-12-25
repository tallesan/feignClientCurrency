package com.example.feignClient.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;

/**
 * Объект URL Для Gif-ки
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "image_url"
})
public class DataGiphy {

  @JsonProperty("image_url")
  private String imageUrl;
}
