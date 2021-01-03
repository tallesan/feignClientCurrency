package com.example.feignClient.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import java.time.LocalDateTime;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Объект для курсов валют
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "disclaimer",
    "license",
    "timestamp",
    "base",
    "rates"
})
public class CurrencyResponse {

  private LocalDateTime dateCurrency;
  private String opponent;
  @JsonProperty("disclaimer")
  private String disclaimer;
  @JsonProperty("license")
  private String license;
  @JsonProperty("timestamp")
  private Integer timestamp;
  @JsonProperty("base")
  private String base;
  @JsonProperty("rates")
  private Map<String, Double> rates;

}
