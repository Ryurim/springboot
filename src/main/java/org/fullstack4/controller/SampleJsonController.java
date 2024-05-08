package org.fullstack4.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@RestController
public class SampleJsonController {
    @GetMapping("/arrhello")
    public String[] arrhello() {
        log.info("=============================================");
        return new String[]{"류림", "뭐", "함?"};
    }
}
