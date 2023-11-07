package com.example.wearVillage.testArea;

import com.example.wearVillage.KyhUtilMethod.dateFormater;

import java.time.LocalDateTime;

public class HSTest {
  public static void main(String[] args) {
    LocalDateTime localDateTime = LocalDateTime.now();

    System.out.println(localDateTime);
    dateFormater formater = new dateFormater(localDateTime.minusDays(2));
    System.out.println(formater.PeriodCalculator());
  }

}
