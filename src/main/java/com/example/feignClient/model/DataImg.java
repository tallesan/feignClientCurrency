package com.example.feignClient.model;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.HashMap;
import java.util.Map;
import lombok.Data;
/**
 * получаем список свойств Gif объекта
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class DataImg {

  @JsonIgnore
  private Map<String, DataGiphy> additionalProperties = new HashMap<>();

  @JsonAnySetter
  public void setAdditionalProperty(String name, DataGiphy value) {
    this.additionalProperties.put(name, value);
  }
}
