package com.dunshan;

import io.micrometer.core.instrument.Counter;
import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


/**
 * ping测试
 */
@RestController
@RequestMapping(value = "/ping")
public class PingController {

  private static final Logger logger = LoggerFactory.getLogger(PingController.class);

  @GetMapping
  @ResponseBody
  public String ping() {
    logger.info("ping controller");

    return "zuul: " + new Date().toString();
  }


}
