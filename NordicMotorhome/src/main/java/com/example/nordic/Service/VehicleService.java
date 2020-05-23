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

    /***
     * Gets the result set to from vehicleRepo
     * @return
     */
    public List<Vehicle> readAll() {
        return vehicleRepo.readAll();
    }

    /***
     * Passes the vehicle to be added to vehicleRepo
     * @param vehicle the vehicle to be added
     */
    public void create(Vehicle vehicle) {
        vehicleRepo.create(vehicle);
    }

    public boolean deleteVehicle(int idVehicle) {
        return vehicleRepo.deleteVehicle(idVehicle);
    }
}
