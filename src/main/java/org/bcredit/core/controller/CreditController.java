package org.bcredit.core.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CreditController {

    @RequestMapping("/getCreditScore")
    public Integer getCreditScore() {
        return 0;
    }

}
