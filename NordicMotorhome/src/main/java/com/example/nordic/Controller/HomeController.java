package com.example.nordic.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    /**
     * Get request for the index page of the application
     * @return index page view
     */
    @GetMapping("/")
    public String index() {
        return "/home/index";
    }
}
