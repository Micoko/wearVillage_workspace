package com.example.wearVillage.Controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Slf4j
@RequiredArgsConstructor
public class errorController {

  @GetMapping("/errorpage")
  public String errorpage(){
    log.info("에러페이지 호출");
    return "errorpage";
  }


}
