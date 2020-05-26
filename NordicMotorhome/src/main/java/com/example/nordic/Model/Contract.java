package com.example.nordic.Model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Contract {
    /*
    Fields
     */
    @Id
    private int idContract;
    private String startDate;
    private String endDate;
    private int endOdometer;
    private boolean fuelCharge;
    private int pickUpKm;
    private int idVehicle;
    private int idCustomer;

    /*
    Getters and setters
     */
    public int getIdContract() {
        return idContract;
    }

    public void setIdContract(int idContract) {
        this.idContract = idContract;
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

    public int getEndOdometer() {
        return endOdometer;
    }

    public void setEndOdometer(int endOdometer) {
        this.endOdometer = endOdometer;
    }

    public boolean isFuelCharge() {
        return fuelCharge;
    }

    public void setFuelCharge(boolean fuelCharge) {
        this.fuelCharge = fuelCharge;
    }

    public int getPickUpKm() {
        return pickUpKm;
    }

    public void setPickUpKm(int pickUpKm) {
        this.pickUpKm = pickUpKm;
    }

    public int getIdVehicle() {
        return idVehicle;
    }

    public void setIdVehicle(int idVehicle) {
        this.idVehicle = idVehicle;
    }

    public int getIdCustomer() {
        return idCustomer;
    }

    public void setIdCustomer(int idCustomer) {
        this.idCustomer = idCustomer;
    }
}
