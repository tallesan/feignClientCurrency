package com.example.feignClient.client;

import com.example.feignClient.model.UserResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(url = "${url}", name = "UserClient")
public interface UserClient {

  @GetMapping(value = "/latest.json?app_id={keyId}&base={base}")
  UserResponse getUser(@PathVariable("keyId") String date, @PathVariable("base") String base);

  @GetMapping(value = "/historical/{date}.json?app_id={keyId}&base={base}")
  UserResponse getUserHistory(@PathVariable("date") String date,
      @PathVariable("keyId") String keyId, @PathVariable("base") String base);

}
