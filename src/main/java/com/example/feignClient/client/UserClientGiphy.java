package com.example.feignClient.client;

import com.example.feignClient.model.DataImg;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(url = "${urlImg}", name = "UserClientGiphy")
public interface UserClientGiphy {

  @GetMapping(value = "/random?api_key={apiKey}&tag={rich}&rating=r")
  DataImg getGiphyRich(@PathVariable("apiKey") String apiKey, @PathVariable("rich") String rich);

}
