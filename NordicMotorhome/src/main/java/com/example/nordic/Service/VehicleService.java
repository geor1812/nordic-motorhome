package com.example.nordic.Service;

import com.example.nordic.Model.Vehicle;
import com.example.nordic.Repository.VehicleRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VehicleService {
    @Autowired
    VehicleRepo vehicleRepo;
    private int workingID;

    /**
     * Gets the result set to from vehicleRepo
     * @return query result set
     */
    public List<Vehicle> readAll() {
        return vehicleRepo.readAll();
    }

    /**
     * Passes the vehicle to be added to vehicleRepo
     * @param vehicle the vehicle to be added
     */
    public void create(Vehicle vehicle) {
        vehicleRepo.create(vehicle);
    }

    /**
     * Sends the id and update vehicle information to vehicleRepo
     * @param id the id of the vehicle to be updated
     * @param vehicle a Vehicle object containing the updated information
     */
    public void updateVehicle(int id, Vehicle vehicle) {
        vehicleRepo.updateVehicle(id, vehicle);
    }

    /**
     * Returns a Vehicle object from the vehicleRepo
     * @param id the id of the vehicle
     **/
    public Vehicle findVehicleById(int id) {
        return vehicleRepo.findVehicleById(id);
    }

    /**
     * Sends the id of the vehicle that will be deleted to vehicleRepo
     * @param idVehicle the id of the vehicle to be deleted
     * @return confirmation of successful deletion
     */
    public boolean deleteVehicle(int idVehicle) {
        return vehicleRepo.deleteVehicle(idVehicle);
    }

    /*
    Working ID Getters & Setters
     */
    public int getWorkingID() {
        return workingID;
    }

    public void setWorkingID(int workingID) {
        this.workingID = workingID;
    }
}
