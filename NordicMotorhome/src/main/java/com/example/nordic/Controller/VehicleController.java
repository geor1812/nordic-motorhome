package com.example.nordic.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/vehicle")
public class VehicleController {

    /***
     * Get request for the vehicle menu page
     * @return vehicleMenu view
     */
    @GetMapping("/vehicleMenu")
    public String vehicleMenu() {
        return "vehicle/vehicleMenu";
    }
}
