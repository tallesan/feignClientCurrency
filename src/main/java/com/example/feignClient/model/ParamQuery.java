package com.example.feignClient.model;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Data
@Component
public class ParamQuery {
  @Value("${param.appId}")
  String appId;
  @Value("${param.base}")
  String base;
  @Value("${param.opponent}")
  String opponent;
  @Value("${param.alternative}")
  String alternative;
}
