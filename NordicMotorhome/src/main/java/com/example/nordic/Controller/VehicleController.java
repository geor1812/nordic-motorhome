package com.example.nordic.Controller;

import com.example.nordic.Model.Contract;
import com.example.nordic.Model.Vehicle;
import com.example.nordic.Service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/vehicle")
public class VehicleController {
    @Autowired
    VehicleService vehicleService;

    /**
     * Created by Team
     * Get request for the vehicle menu page
     * @return vehicleMenu view
     */
    @GetMapping("/vehicleMenu")
    public String vehicleMenuGet(Model model) {
        List<Vehicle> vehicleList = vehicleService.readAll();
        model.addAttribute("vehicles", vehicleList);
        return "vehicle/vehicleMenu";
    }

    /**
     * Created by Team
     * Post method which gets the search term inserted by the user and retrieves
     * a list of vehicles filtered down to contain the search term
     * @param webRequest used to retrieve the input from the view
     * @param model used to pass the information from the controller to the view
     * @return vehicleMenuSearch page
     */
    @PostMapping("/vehicleMenu")
    public String vehicleMenuPost (WebRequest webRequest, Model model){
       String search = webRequest.getParameter("search");
       List<Vehicle> vehicleList = vehicleService.readSearch(search);
       model.addAttribute("vehicles", vehicleList);
       return "vehicle/vehicleMenuSearch";
    }

    /**
     * Created by George
     * Get request for the create vehicle page
     * @param vehicle used for Thymealeaf validation
     * @return createVehicle view
     */
    @GetMapping("/createVehicle")
    public String createVehicleGet(Vehicle vehicle) {
        return "vehicle/createVehicle";
    }

    /**
     * Created by Max
     * Get request for the update vehicle page
     * @param idVehicle the id of the vehicle to be updated
     * @param model used to pass the information from the controller to the view
     * @return updateVehicle view
     */
    @GetMapping("/updateVehicle/{idVehicle}")
    public String updateVehicleGet(@PathVariable("idVehicle") int idVehicle, Model model) {
        vehicleService.setWorkingID(idVehicle);
        model.addAttribute("vehicle", vehicleService.findVehicleById(idVehicle));
        return "vehicle/updateVehicle";
    }

    /**
     * Created by Max
     * Post method which gets the updated information
     * @param vehicle the vehicle to be updated
     * @return redirects to the VehicleMenu view
     */
    @PostMapping("/updateVehicle")
    public String updateVehiclePost(@ModelAttribute Vehicle vehicle){
        int id = vehicleService.getWorkingID();
        vehicleService.updateVehicle(id, vehicle);
        return "redirect:/vehicle/vehicleMenu";
    }

    /**
     * Created by George
     * Post method which takes the information for the new vehicle to be added
     * from a form in the view and sends it to vehicleService
     * @param vehicle the vehicle to be created
     * @param bindingResult used to validate input
     * @return redirects to the vehicleMenu view
     */
    @PostMapping("/createVehicle")
    public String createVehiclePost(@ModelAttribute @Valid Vehicle vehicle, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            return "vehicle/createVehicle";
        } else {
            vehicleService.create(vehicle);
            return "redirect:/vehicle/vehicleMenu";
        }
    }

    /**
     * Created by Johan
     * Get request which gets the id of the vehicle to be deleted
     * @param idVehicle the id of the vehicle to be deleted
     * @return redirects to the vehicleMenu view
     */
    @GetMapping("/deleteVehicle/{idVehicle}")
    public String deleteVehicleGet(@PathVariable("idVehicle") int idVehicle) {
        vehicleService.deleteVehicle(idVehicle);
        return "redirect:/vehicle/vehicleMenu";
    }

    /**
     * Created by Remi
     * Get request for viewing details on a vehicle
     * @param idVehicle id of the vehicle for which the details are displayed
     * @param model used to pass the information from the controller to the view
     * @return viewDetails page
     */
    @GetMapping("/viewDetails/{idVehicle}")
    public String viewDetails(@PathVariable("idVehicle") int idVehicle, Model model){
        model.addAttribute("vehicle", vehicleService.findVehicleById(idVehicle));
        return "vehicle/viewDetails";
    }
}
