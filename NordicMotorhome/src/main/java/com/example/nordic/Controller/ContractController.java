package com.example.nordic.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/contract")
public class ContractController {

    @GetMapping("/contractMenu")
    public String contractMenuGet() {
        return "contract/contractMenu";
    }
}
