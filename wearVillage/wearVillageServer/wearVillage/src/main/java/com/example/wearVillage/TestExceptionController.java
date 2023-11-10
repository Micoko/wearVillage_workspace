package com.example.wearVillage;

import com.example.wearVillage.exception.CustomException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Slf4j
@Controller
@RequestMapping("/test")
public class TestExceptionController {

    @ResponseBody
    @GetMapping("/t1/{err}")
    public String t1(@PathVariable String err) {

        if(err.equals("1")){

        }else{
            throw new CustomException("예외1");
        }

        return "test";
    }
}
