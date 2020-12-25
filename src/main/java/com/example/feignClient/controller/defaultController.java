package com.example.feignClient.controller;

import com.example.feignClient.model.UserResponse;
import com.example.feignClient.service.ServiceClientImpl;
import com.example.feignClient.service.ServiceGiphy;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class defaultController {

  private ServiceClientImpl serviceClient;
  private ServiceGiphy serviceGiphy;

  @Autowired
  public defaultController(ServiceClientImpl serviceClient, ServiceGiphy serviceGiphy) {
    this.serviceClient = serviceClient;
    this.serviceGiphy = serviceGiphy;
  }

  @GetMapping("/")
  public String getStartProg(Model model) {
    return "redirect:/index";
  }

  @RequestMapping("/index")
  public String initClient(Model model) {
    UserResponse thisDay = serviceClient.getThisDay();
    UserResponse historyDay = serviceClient.getHistoryDay();
    boolean comparison = serviceClient
        .getCompareMoney(thisDay.getRates(), historyDay.getRates());
    String img;
    if (comparison) {
      img = serviceGiphy.getUrlGiphy("rich");
    } else {
      img = serviceGiphy.getUrlGiphy("broke");
    }
    model.addAttribute("history", historyDay);
    model.addAttribute("thisDay", thisDay);
    model.addAttribute("img", img);
    model.addAttribute("searchFalse", comparison);
    return "index";
  }


  @GetMapping("/commentRub")
  public String getRubChange(Model model) {
    UserResponse thisDay = serviceClient.changeMoney(serviceClient.getThisDay());
    UserResponse historyDay = serviceClient.changeMoney(serviceClient.getHistoryDay());
    boolean comparison = serviceClient
        .getCompareMoney(thisDay.getRates(), historyDay.getRates());
    String img;
    if (comparison) {
      img = serviceGiphy.getUrlGiphy("rich");
    } else {
      img = serviceGiphy.getUrlGiphy("broke");
    }

    model.addAttribute("history", historyDay);
    model.addAttribute("thisDay", thisDay);
    model.addAttribute("img", img);
    model.addAttribute("searchFalse", comparison);
    return "rub_compare";
  }

}
