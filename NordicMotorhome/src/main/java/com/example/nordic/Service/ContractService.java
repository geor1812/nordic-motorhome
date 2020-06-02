package com.example.nordic.Service;

import com.example.nordic.Model.Contract;
import com.example.nordic.Model.Vehicle;
import com.example.nordic.Repository.ContractRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.concurrent.TimeUnit;

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
    private double workingOdometerCharge;
    private double workingPickUpCharge;
    private boolean workingFuelCharge;

    public String createContract(Contract contract){return contractRepo.createContract(contract);}

    /**
     * Link between the controller and the repo for readAll
     * @return result of contractRepo.readAll()
     */
    public List<Contract> readAll() {
        return contractRepo.readAll();
    }

    /**
     * Link between the controller and the repo for readSearch
     * @param search search term to check for
     * @return result of contractRepo.readSearch()
     */
    public List<Contract> readSearch(String search) {
        return contractRepo.readSearch(search);
    }

    /**
     * Link between the controller and the repo for findContractById
     * @param id id of the contract to be found
     * @return result of contractRepo.findContractById()
     */
    public Contract findContractById(int id) {
        return contractRepo.findContractById(id);
    }

    /**
     * Link between the controller and the repo for updateContract
     * @param id id of the contract to be updated
     * @param contract contract containing the information of update
     */
    public void updateContract(int id, Contract contract) {
        contractRepo.updateContract(id, contract);
    }

    /**
     * Link between the controller and the repo for
     * @param idContract id of the contract to be deleted
     */
    public void deleteContract(int idContract) {
        contractRepo.deleteContract(idContract);
    }

    /**
     * Method for calculating the total price of all accessories on a contract, per day
     * @param id id of the contract that has the accessories
     * @return total price of all accessories per day
     */
    public int accesoriesPricePerDay(int id) {
        Contract contract = findContractById(id);
        return (contract.getBedLinen() * 1) + (contract.getBikeRack() * 2) +
                (contract.getChildSeat() * 1) + (contract.getGrill() * 3) +
                (contract.getChair() * 1) + (contract.getTble() * 3);
    }

    /**
     * Method for calculating the total price of a contract.
     * @param id id of the contract, the price needs to be calculated for.
     * @return the total price of the contract
     */
    public int totalPrice(int id){
        Contract contract = findContractById(id);
        Vehicle vehicle = vehicleService.findVehicleById(contract.getIdVehicle());

        // get amount of days by subtracting the start date from the end date
        long amountOfDays = daysBetweenDays(contract.getEndDate(), contract.getStartDate());
        // get the total price per day for accessories on the contract eg. (id)
        int accessoriesTotalPerDay = accesoriesPricePerDay(id);
        // get the price of the vehicle per day
        int vehiclePricePerDay = vehicle.getPricePerDay();

        Date d = null;
        try {
            d = new SimpleDateFormat("yyyy-MM-dd").parse(contract.getStartDate());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        //Instantiate callender
        Calendar myCal = new GregorianCalendar();
        //set the date equal to the start date
        myCal.setTime(d);

        //Make month equal to their corresponding number, since MONTH is indexed at 0
        int month = myCal.get((Calendar.MONTH) + 1);

        //Set the vehicle price according to the season
        if(month == 3 || month == 4 || month == 10 || month == 11) {
            vehiclePricePerDay = vehiclePricePerDay + ((vehiclePricePerDay * 30) / 100);
        } else if(month == 5 || month == 6 || month == 7 || month == 8 || month == 9) {
            vehiclePricePerDay = vehiclePricePerDay + ((vehiclePricePerDay * 60) / 100);
        }
        //total price per day = Vehicle price per day + Accessories price per day
        int pricePerDay = accessoriesTotalPerDay + vehiclePricePerDay;
        //Total price is price per day times the amount of days on the contract
        int totalPrice = (int) (pricePerDay * amountOfDays);

        return totalPrice;
    }

    /**
     * Calculates the days between 2 dates.
     * @param date1 later date
     * @param date2 earlier date to be subtracted
     * @return amountOfDays converted from MILLISECONDS to DAYS
     */
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

    /**
     * Method for calculating the cancellation fee of a contract
     * @param id id of the contract to be cancelled
     * @param currentDate date of the day the contract is cancelled
     * @return cancellationFee
     */
    public double cancellation(int id, String currentDate){
        Contract contract = findContractById(id);
        long amountOfDays = daysBetweenDays(contract.getStartDate(), currentDate);
        // total price of the contract, calculated by the totalPrice() method
        int totalPrice = totalPrice(id);
        double cancellationFee = 0;

        /* calculate the percentage of the total price that the fee should be
            based on how many days there is til the start of the contract
         */
        if(amountOfDays < 1){
            cancellationFee = (double) totalPrice / 100 * 95;
        } else if (amountOfDays < 15 && amountOfDays >= 1){
            cancellationFee = (double) totalPrice / 100 * 80;
        } else if (amountOfDays <= 49 && amountOfDays >= 15){
            cancellationFee = (double) totalPrice / 100 * 50;
        } else {
            cancellationFee = (double) totalPrice / 100 * 20;
        }

        if(cancellationFee < 200){
            cancellationFee = 200.00;
        }

        //Getting the cancellation fee with 1 decimal, EG. 0,0 instead of 0,000000001
        cancellationFee = cancellationFee * 100;
        cancellationFee = Math.round(cancellationFee);
        cancellationFee = cancellationFee / 100;
        return cancellationFee;
    }

    /**
     * Method for calculating the final price at checkout
     * @param id id of the contract to be checked out
     * @param endOdometer the number on the odometer at the end of the contract
     * @param pickUpKm amount of kilometers from the office, where the vehicle is dropped of
     * @param fuelCharge if the fuel level of the vehicle is below 50% on pickup
     * @return price
     */
    public double checkout(int id, int endOdometer, int pickUpKm, boolean fuelCharge) {
        Contract contract = findContractById(id);
        Vehicle vehicle = vehicleService.findVehicleById(contract.getIdVehicle());

        long amountOfDays = daysBetweenDays(contract.getEndDate(), contract.getStartDate());
        double price = totalPrice(id);

        if(fuelCharge)  {
            price += 70;
        }

        double odometerCharge = 0;
        double kmDriven = endOdometer - vehicle.getOdometer();
        //Checks if the odometer has an average of more than 400 km per day, if so adds the extra kilometers as a fee
        if((kmDriven / amountOfDays) > 400) {
            odometerCharge = ((kmDriven / amountOfDays) - 400) * amountOfDays;
        } else {
            odometerCharge = 0;
        }
        setWorkingOdometerCharge(odometerCharge);
        price += odometerCharge;

        //pickup charge = kilometers to office * 0.7 euros
        double pickUpCharge = pickUpKm * 0.7;
        price += pickUpCharge;
        setWorkingPickUpCharge(pickUpCharge);

        return price;
    }

    /**
     * Link between the controller and the repo for archiving contracts
     * @param id id of the contract to be archived
     * @param fee fee of the contract to be archived
     * @param odometerCharge odometer charge of the contract to be archived
     * @param pickUpCharge pickup charge of the contract to be archived
     * @param fuelCharge fuel charge of the contract to be archived
     */
    public void archiveContract(int id, double fee, double odometerCharge, double pickUpCharge, boolean fuelCharge){
        Contract contract = findContractById(id);
        contractRepo.archiveContract(contract, fee, odometerCharge, pickUpCharge, fuelCharge);
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

    public int getWorkingID() {
        return workingID;
    }

    public void setWorkingID(int workingID) {
        this.workingID = workingID;
    }

    public double getWorkingOdometerCharge() {
        return workingOdometerCharge;
    }

    public void setWorkingOdometerCharge(double workingOdometerCharge) {
        this.workingOdometerCharge = workingOdometerCharge;
    }

    public double getWorkingPickUpCharge() {
        return workingPickUpCharge;
    }

    public void setWorkingPickUpCharge(double workingPickUpCharge) {
        this.workingPickUpCharge = workingPickUpCharge;
    }

    public boolean isWorkingFuelCharge() {
        return workingFuelCharge;
    }

    public void setWorkingFuelCharge(boolean workingFuelCharge) {
        this.workingFuelCharge = workingFuelCharge;
    }

    public double getWorkingFee() {
        return workingFee;
    }

    public void setWorkingFee(double workingFee) {
        this.workingFee = workingFee;
    }

    /**
     * Link between the controller and the repo for creating
     * @param idContract
     * @param idLicence
     */
    public void createLc(int idContract, int idLicence) {
        contractRepo.createLc(idContract, idLicence);
    }

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

    /**
     * List of vehicles that is available between 2 dates, that also has a given number of beds.
     * @param startDate first date
     * @param endDate second date
     * @param numberOfBeds amount of beds in the vehicles of the list
     * @return vehicles (eg. the vehicles that are available in the given timeframe)
     */
    public List<Vehicle> vehiclesFromContractList(int numberOfBeds, String startDate, String endDate){
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

