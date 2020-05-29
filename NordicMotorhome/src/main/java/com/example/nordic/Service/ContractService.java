package com.example.nordic.Service;

import com.example.nordic.Model.Contract;
import com.example.nordic.Model.Vehicle;
import com.example.nordic.Repository.ContractRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ContractService {
    @Autowired
    ContractRepo contractRepo;
    @Autowired
    VehicleService vehicleService;
    private int workingID;

    int idCustomer;
    int numberOfBeds;
    private String startDate;
    private String endDate;

    public String createContract(Contract contract){return contractRepo.createContract(contract);}

    public List<Contract> readAll() {
        return contractRepo.readAll();
    }

    public List<Contract> readSearch(String search) {
        return contractRepo.readSearch(search);
    }

    public Contract findContractById(int id) {
        return contractRepo.findContractById(id);
    }

    public void updateContract(int id, Contract contract) {
        contractRepo.updateContract(id, contract);
    }

    public int getWorkingID() {
        return workingID;
    }

    public void setWorkingID(int workingID) {
        this.workingID = workingID;
    }

    public void deleteContract(int idContract) {
        contractRepo.deleteContract(idContract);
    }

    public int getIdCustomer() {
        return idCustomer;
    }

    public void setIdCustomer(int idCustomer) {
        this.idCustomer = idCustomer;
    }

    public int getNumberOfBeds() {
        return numberOfBeds;
    }

    public void setNumberOfBeds(int numberOfBeds) {
        this.numberOfBeds = numberOfBeds;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }


    public void createLc(int idContract, int idLicence) { contractRepo.createLc(idContract, idLicence); }

    //returns a list of contracts where the dates dont clash
    public List<Contract> availableDatesList(String startDate, String endDate) {
        Date start = null;
        Date end = null;
        try {
            start = new SimpleDateFormat("yyyy-MM-dd").parse(startDate);
            end = new SimpleDateFormat("yyyy-MM-dd").parse(endDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        List<Contract> contracts = contractRepo.readAll();
        for (int i = 0; i < contracts.size(); i++) {
            Date contractStart = null;
            Date contractEnd = null;
            try {
                contractStart = new SimpleDateFormat("yyyy-MM-dd").parse(contracts.get(i).getStartDate());
                contractEnd = new SimpleDateFormat("yyyy-MM-dd").parse(contracts.get(i).getEndDate());
            } catch (ParseException e) {
                e.printStackTrace();
            }

            if (end.getTime() < contractStart.getTime() ||
                start.getTime() > contractEnd.getTime()){
                contracts.remove(contracts.get(i));
                i = i - 1;

            }
        }

        return contracts;
    }

    public List<Vehicle> vehiclesFromContractList(String startDate, String endDate, int numberOfBeds){
        List<Contract> contracts = availableDatesList(startDate, endDate);
        List<Vehicle> vehicles = vehicleService.availableVehiclesList(numberOfBeds);
        for (int i = 0; i < vehicles.size(); i++) {
            for (Contract contract:contracts) {
                if (contract.getIdVehicle() == vehicles.get(i).getIdVehicle()) {
                    vehicles.remove(i);
                    i = i - 1;
                }
            }
        }
        return vehicles;
    }
}