package com.example.nordic.Service;

import com.example.nordic.Model.Contract;
import com.example.nordic.Model.Vehicle;
import com.example.nordic.Repository.VehicleRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class VehicleService {
    @Autowired
    VehicleRepo vehicleRepo;
    private int workingID;

    /**
     * Created by Team
     * Gets the result set to from vehicleRepo
     * @return query result set
     */
    public List<Vehicle> readAll() {
        return vehicleRepo.readAll();
    }

    /**
     * Created by Team
     * Passes the search term to the vehicleRepo and then returns the result set
     * @param search the search term
     * @return query result set
     */
    public List<Vehicle> readSearch(String search) {
        return vehicleRepo.readSearch(search);
    }

    /**
     * Created by George
     * Passes the vehicle to be added to vehicleRepo
     * @param vehicle the vehicle to be added
     */
    public void create(Vehicle vehicle) {
        vehicleRepo.create(vehicle);
    }

    /**
     * Created by Max
     * Sends the id and update vehicle information to vehicleRepo
     * @param id the id of the vehicle to be updated
     * @param vehicle a Vehicle object containing the updated information
     */
    public void updateVehicle(int id, Vehicle vehicle) {
        vehicleRepo.updateVehicle(id, vehicle);
    }

    /**
     * Created by Remi
     * Returns a Vehicle object from the vehicleRepo
     * @param id the id of the vehicle
     **/
    public Vehicle findVehicleById(int id) {
        return vehicleRepo.findVehicleById(id);
    }

    /**
     * Created by Johan
     * Sends the id of the vehicle that will be deleted to vehicleRepo
     * @param idVehicle the id of the vehicle to be deleted
     */
    public void deleteVehicle(int idVehicle) {
        vehicleRepo.deleteVehicle(idVehicle);
    }

    /**
     * Created by George
     * Method to make a list of vehicles that is directly related to the list of contracts
     * @param contractList list of contracts
     * @return list of vehicles that is directly related to the list of contracts
     */
    public List<Vehicle> fromContracts(List<Contract> contractList) {
        List<Vehicle> vehicleList = new ArrayList<Vehicle>();
        for (Contract contract: contractList) {
            vehicleList.add(vehicleRepo.findVehicleById(contract.getIdVehicle()));
        }
        return vehicleList;
    }

    /**
     * Created by Remi
     * Link between the controller and the repo for available vehicles
     * @param numberOfBeds number of beds required in the list of available vehicles
     * @return list of available vehicles with given amount of beds
     */
    public List<Vehicle> availableVehiclesList(int numberOfBeds){return vehicleRepo.availableVehiclesList(numberOfBeds);}


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
