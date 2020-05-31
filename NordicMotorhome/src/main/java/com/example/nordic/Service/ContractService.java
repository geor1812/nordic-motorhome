package com.example.nordic.Service;

import com.example.nordic.Model.Contract;
import com.example.nordic.Model.Vehicle;
import com.example.nordic.Repository.ContractRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sun.util.resources.cldr.aa.CalendarData_aa_ER;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static java.util.concurrent.TimeUnit.MILLISECONDS;

@Service
public class ContractService {
    @Autowired
    ContractRepo contractRepo;
    @Autowired
    VehicleService vehicleService;

    private double workingFee;
    private int workingID;
    private String startDate;
    private String endDate;
    private int workingEndOdometer;
    private int workingPickUpKm;
    private boolean workingFuelCharge;

    public void createContract(Contract contract){contractRepo.createContract(contract);}

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

    public void deleteContract(int idContract) {
        contractRepo.deleteContract(idContract);
    }

    public int accesoriesPricePerDay(int id) {
        Contract contract = findContractById(id);
        return (contract.getBedLinen() * 5) + (contract.getBikeRack() * 8) +
                (contract.getChildSeat() * 4) + (contract.getGrill() * 10) +
                (contract.getChair() * 4) + (contract.getTble() * 10);
    }

    public int totalPrice(int id){
        Contract contract = findContractById(id);
        Vehicle vehicle = vehicleService.findVehicleById(contract.getIdVehicle());

        long amountOfDays = daysBetweenDays(contract.getEndDate(), contract.getStartDate());
        int accessoriesTotalPerDay = accesoriesPricePerDay(id);
        int vehiclePricePerDay = vehicle.getPricePerDay();

        Date d = null;
        try {
            d = new SimpleDateFormat("yyyy-MM-dd").parse(contract.getStartDate());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Calendar myCal = new GregorianCalendar();
        myCal.setTime(d);

        int month = myCal.get((Calendar.MONTH) + 1);

        if(month == 3 || month == 4 || month == 10 || month == 11) {
            vehiclePricePerDay = vehiclePricePerDay + ((vehiclePricePerDay * 30) / 100);
        } else if(month == 5 || month == 6 || month == 7 || month == 8 || month == 9) {
            vehiclePricePerDay = vehiclePricePerDay + ((vehiclePricePerDay * 60) / 100);
        }

        int pricePerDay = accessoriesTotalPerDay + vehiclePricePerDay;
        int totalPrice = (int) (pricePerDay * amountOfDays);

        return totalPrice;
    }

    public long daysBetweenDays(String date1, String date2) {
        Date d1 = null;
        Date d2 = null;
        try {
            d1 = new SimpleDateFormat("yyyy-MM-dd").parse(date1);
            d2 = new SimpleDateFormat("yyyy-MM-dd").parse(date2);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        long amountOfDays = d1.getTime() - d2.getTime();
        return TimeUnit.DAYS.convert(amountOfDays, TimeUnit.MILLISECONDS);
    }

    public double cancellation(int id, String currentDate){
        Contract contract = findContractById(id);
        long amountOfDays = daysBetweenDays(contract.getStartDate(), currentDate);
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

    public double checkout(int id, int endOdometer, int pickUpKm, boolean fuelCharge) {
        Contract contract = findContractById(id);
        Vehicle vehicle = vehicleService.findVehicleById(contract.getIdVehicle());

        long amountOfDays = daysBetweenDays(contract.getEndDate(), contract.getStartDate());
        double price = totalPrice(id);

        if(fuelCharge)  {
            price += 50;
        }

        double kmDriven = endOdometer - vehicle.getOdometer();
        if((kmDriven / amountOfDays) > 400) {
            price += ((kmDriven / amountOfDays) - 400) * amountOfDays;
        }

        price += pickUpKm * 0.7;

        return price;
    }

    public void archiveContract(int id, double fee){
        Contract contract = findContractById(id);
        contractRepo.archiveContract(contract, fee);
        contractRepo.deleteContract(id);
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

    public double getWorkingFee() {
        return workingFee;
    }

    public void setWorkingFee(double workingFee) {
        this.workingFee = workingFee;
    }

    public int getWorkingID() {
        return workingID;
    }

    public void setWorkingID(int workingID) {
        this.workingID = workingID;
    }

    public int getWorkingEndOdometer() {
        return workingEndOdometer;
    }

    public void setWorkingEndOdometer(int workingEndOdometer) {
        this.workingEndOdometer = workingEndOdometer;
    }

    public int getWorkingPickUpKm() {
        return workingPickUpKm;
    }

    public void setWorkingPickUpKm(int workingPickUpKm) {
        this.workingPickUpKm = workingPickUpKm;
    }

    public boolean isWorkingFuelCharge() {
        return workingFuelCharge;
    }

    public void setWorkingFuelCharge(boolean workingFuelCharge) {
        this.workingFuelCharge = workingFuelCharge;
    }
}