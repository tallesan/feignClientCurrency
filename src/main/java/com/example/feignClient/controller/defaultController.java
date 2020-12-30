package com.example.feignClient.controller;

import com.example.feignClient.model.CurrencyResponse;
import com.example.feignClient.service.ServiceClientImpl;
import com.example.feignClient.service.ServiceGiphy;
import java.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class defaultController {

  private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy");
  private final ServiceClientImpl serviceClient;
  private final ServiceGiphy serviceGiphy;

  @Autowired
  public defaultController(ServiceClientImpl serviceClient, ServiceGiphy serviceGiphy) {
    this.serviceClient = serviceClient;
    this.serviceGiphy = serviceGiphy;
  }

  /**
   * Стартуем клиент
   */
  @GetMapping("/")
  public String getStartProg(Model model) {
    return "redirect:/index";
  }

  /**
   * выводим значение для базовой валюты
   */
  @RequestMapping("/index")
  public String initClient(Model model) {
    CurrencyResponse thisDay = serviceClient.getThisDay();
    CurrencyResponse historyDay = serviceClient.getHistoryDay();
    String behavior;
    String img;
    if (serviceClient
        .getCompareMoney(thisDay.getRates(), historyDay.getRates())) {
      img = serviceGiphy.getUrlGiphy("broke");
      behavior = "вниз";
    } else {
      img = serviceGiphy.getUrlGiphy("rich");
      behavior = "вверх";
    }
    model.addAttribute("thisDayDate", thisDay.getDateCurrency().format(FORMATTER));
    model.addAttribute("historyDayDate", historyDay.getDateCurrency().format(FORMATTER));
    model.addAttribute("history", historyDay);
    model.addAttribute("thisDay", thisDay);
    model.addAttribute("img", img);
    model.addAttribute("behavior",behavior);
    return "index";
  }

  /**
   * выводим значение для альтернативной валюты
   */
  @GetMapping("/commentRub")
  public String getRubChange(Model model) {
    CurrencyResponse thisDay = serviceClient.changeMoney(serviceClient.getThisDay());
    CurrencyResponse historyDay = serviceClient.changeMoney(serviceClient.getHistoryDay());
    String behavior;
    String img;
    if (serviceClient
        .getCompareMoney(thisDay.getRates(), historyDay.getRates())) {
      img = serviceGiphy.getUrlGiphy("broke");
      behavior = "вниз";
    } else {
      img = serviceGiphy.getUrlGiphy("rich");
      behavior = "вверх";
    }
    model.addAttribute("thisDayDate", thisDay.getDateCurrency().format(FORMATTER));
    model.addAttribute("historyDayDate", historyDay.getDateCurrency().format(FORMATTER));
    model.addAttribute("history", historyDay);
    model.addAttribute("thisDay", thisDay);
    model.addAttribute("img", img);
    model.addAttribute("behavior",behavior);
    return "rub_compare";
  }

}
