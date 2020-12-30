package com.example.feignClient.service;


import com.example.feignClient.client.GiphyClient;
import com.example.feignClient.model.DataImg;
import com.example.feignClient.model.GiphyParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ServiceGiphyImpl implements ServiceGiphy {

  private final GiphyClient giphyClient;
  private final GiphyParam giphyParam;

  @Autowired
  public ServiceGiphyImpl(GiphyClient giphyClient,
      GiphyParam giphyParam) {
    this.giphyClient = giphyClient;
    this.giphyParam = giphyParam;
  }

  /**
   * получаем Gif-ку
   */
  @Override
  public String getUrlGiphy(String str) {
    DataImg dataImg = giphyClient.getGiphyRich(giphyParam.getApiKey(), str);
    return dataImg.getAdditionalProperties().get("data").getImageUrl();
  }
}
