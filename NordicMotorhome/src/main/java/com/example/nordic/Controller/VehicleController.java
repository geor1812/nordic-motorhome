package com.example.nordic.Controller;

import com.example.nordic.Model.Vehicle;
import com.example.nordic.Service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public String vehicleMenu(Model model) {
        List<Vehicle> vehicleList = vehicleService.readAll();
        model.addAttribute("vehicles", vehicleList);
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
     * Get request for the update vehicle page
     * @param idVehicle the id of the vehicle to be updated
     * @param model
     * @return updateVehicle view
     */
    @GetMapping("/updateVehicle/{idVehicle}")
    public String updateVehicle(@PathVariable("idVehicle") int idVehicle, Model model) {
        vehicleService.setWorkingID(idVehicle);
        model.addAttribute("vehicle", vehicleService.findVehicleById(idVehicle));
        return "vehicle/updateVehicle";
    }

    /**
     * Post method which gets the updated information
     * @param vehicle the vehicle to be updated
     * @return redirects to the VehicleMenu view
     */
    @PostMapping("/updateVehicle")
    public String updateVehicle(@ModelAttribute Vehicle vehicle){
        int id = vehicleService.getWorkingID();
        vehicleService.updateVehicle(id, vehicle);
        return "redirect:/vehicle/vehicleMenu";
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

    @GetMapping("/deleteVehicle/{idVehicle}")
    public String deleteVehicle(@PathVariable("idVehicle") int idVehicle) {
        boolean deleted = vehicleService.deleteVehicle(idVehicle);
        return "redirect:/vehicle/vehicleMenu";
    }
}
