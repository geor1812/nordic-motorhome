package com.example.nordic.Controller;

import com.example.nordic.Model.Vehicle;
import com.example.nordic.Service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/vehicle")
public class VehicleController {
    @Autowired
    VehicleService vehicleService;

    /**
     * Get request for the vehicle menu page
     * @return vehicleMenu view
     */
    @GetMapping("/vehicleMenu")
    public String vehicleMenu() {
        return "vehicle/vehicleMenu";
    }

    /**
     * Get request for the create vehicle page
     * @return createVehicle view
     */
    @GetMapping("/createVehicle")
    public String createVehicleGet() {
        return "vehicle/createVehicle";
    }

    /**
     * Post method which takes the information for the new vehicle to be added
     * from a from in the view and sends it to vehicleService
     * @param vehicle the vehicle to be created
     * @return redirects to the vehicleMenu view
     */
    @PostMapping("/createVehicle")
    public String createVehiclePost(@ModelAttribute Vehicle vehicle) {
        vehicleService.create(vehicle);
        return "redirect:/vehicle/vehicleMenu";
    }
}
