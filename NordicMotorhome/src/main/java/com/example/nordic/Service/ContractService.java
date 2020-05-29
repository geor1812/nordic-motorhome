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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static java.util.concurrent.TimeUnit.MILLISECONDS;

@Service
public class ContractService {
    @Autowired
    ContractRepo contractRepo;
    @Autowired
    VehicleService vehicleService;
    private int workingID;

    private double workingFee;
    private int workingID;

    public double getWorkingFee() {
        return workingFee;
    }

    public void setWorkingFee(double workingFee) {
        this.workingFee = workingFee;
    }


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



    public int getWorkingID() {
        return workingID;
    }

    public void setWorkingID(int workingID) {
        this.workingID = workingID;
    }

    public int totalPrice(int id){
        Contract contract = findContractById(id);
        Vehicle vehicle = vehicleService.findVehicleById(contract.getIdVehicle());

        Date startDate = null;
        Date endDate = null;
        try {
            startDate = new SimpleDateFormat("yyyy-MM-dd").parse(contract.getStartDate());
            endDate = new SimpleDateFormat("yyyy-MM-dd").parse(contract.getEndDate());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        long amountOfDays = endDate.getTime() - startDate.getTime();
        amountOfDays = TimeUnit.DAYS.convert(amountOfDays, TimeUnit.MILLISECONDS);

        int accessoriesTotalPerDay = (contract.getBedLinen() * 5) + (contract.getBikeRack() * 8) + (contract.getChildSeat() * 4) + (contract.getGrill() * 10) + (contract.getChair() * 4) + (contract.getTble() * 10);
        int vehiclePricePerDay = vehicle.getPricePerDay();
        int pricePerDay = accessoriesTotalPerDay + vehiclePricePerDay;
        int totalPrice = (int) (pricePerDay * amountOfDays);

        return totalPrice;
    }

    public double cancellation(int id, String currentDate){

        Contract contract = findContractById(id);
        Date cancellationDate = null;
        Date startDate = null;
        try {
            cancellationDate = new SimpleDateFormat("yyyy-MM-dd").parse(currentDate);
            startDate = new SimpleDateFormat("yyyy-MM-dd").parse(contract.getStartDate());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        long amountOfDays = startDate.getTime() - cancellationDate.getTime();
        amountOfDays = TimeUnit.DAYS.convert(amountOfDays, TimeUnit.MILLISECONDS);

        int totalPrice = totalPrice(id);
        double cancellationFee = 0;

        if(amountOfDays < 1){
            cancellationFee = (double) totalPrice / 100 * 95;
        } else if (amountOfDays < 15 && amountOfDays >= 1){
            cancellationFee = (double) totalPrice / 100 * 80;
        } else if (amountOfDays <= 49 && amountOfDays >= 15){
            cancellationFee = (double) totalPrice / 100 * 50;
        } else {
            cancellationFee = (double) totalPrice / 100 * 20;
            if(cancellationFee < 200){
                cancellationFee = 200.00;
            }
        }
        cancellationFee = cancellationFee * 100;
        cancellationFee = Math.round(cancellationFee);
        cancellationFee = cancellationFee / 100;
        return cancellationFee;
    }

    public void archiveContract(int id, double fee){
        Contract contract = findContractById(id);
        contractRepo.archiveContract(contract, fee);
        contractRepo.deleteContract(id);
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