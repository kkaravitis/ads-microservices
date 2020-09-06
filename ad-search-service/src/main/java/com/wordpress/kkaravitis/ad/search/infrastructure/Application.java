package com.wordpress.kkaravitis.ad.search.infrastructure;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;

@ComponentScan("com.wordpress.kkaravitis.ad.search")
@SpringBootApplication
@EnableAsync
public class Application {

  /**
   * Main method
   * 
   * @param args
   */
  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }

}
