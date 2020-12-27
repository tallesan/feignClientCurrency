package com.example.feignClient.client;

import com.example.feignClient.model.UserResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * Клиент для получения JSON курса валют
 */
@FeignClient(url = "${url}", name = "UserClient")
public interface UserClient {

  /**
   * Текущий курс
   */
  @GetMapping(value = "/latest.json?app_id={keyId}&base={base}")
  UserResponse getUser(@PathVariable("keyId") String keyId, @PathVariable("base") String base);

  /**
   * Курс на заданную дату
   */
  @GetMapping(value = "/historical/{date}.json?app_id={keyId}&base={base}")
  UserResponse getUserHistory(@PathVariable("date") String date,
      @PathVariable("keyId") String keyId, @PathVariable("base") String base);

}
