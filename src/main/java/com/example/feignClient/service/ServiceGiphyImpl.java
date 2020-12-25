package com.example.feignClient.service;


import com.example.feignClient.client.UserClientGiphy;
import com.example.feignClient.model.DataImg;
import com.example.feignClient.model.GiphyParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ServiceGiphyImpl implements ServiceGiphy {

  private final UserClientGiphy userClientGiphy;
  private final GiphyParam giphyParam;

  @Autowired
  public ServiceGiphyImpl(UserClientGiphy userClientGiphy,
      GiphyParam giphyParam) {
    this.userClientGiphy = userClientGiphy;
    this.giphyParam = giphyParam;
  }

  /**
   * получаем Gif-ку
   */
  @Override
  public String getUrlGiphy(String str) {
    DataImg dataImg = userClientGiphy.getGiphyRich(giphyParam.getApiKey(), str);
    return dataImg.getAdditionalProperties().get("data").getImageUrl();
  }
}
