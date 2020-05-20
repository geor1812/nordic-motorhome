package com.example.nordic.Model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Vehicle {
    //Fields
    @Id
    private int idVehicle;
    private String regNo;
    private String regDate;
    private int odometer;
    private boolean repairStatus;
    private int idModel;
    private String brand;
    private String modelType;
    private String fuelType;
    private int noBeds;
    private int pricePerDay;

    //Constructor
    public Vehicle() {}

    //Getters & Setters
    public int getIdVehicle() {
        return idVehicle;
    }

    public void setIdVehicle(int idVehicle) {
        this.idVehicle = idVehicle;
    }

    public String getRegNo() {
        return regNo;
    }

    public void setRegNo(String regNo) {
        this.regNo = regNo;
    }

    public String getRegDate() {
        return regDate;
    }

    public void setRegDate(String regDate) {
        this.regDate = regDate;
    }

    public int getOdometer() {
        return odometer;
    }

    public void setOdometer(int odometer) {
        this.odometer = odometer;
    }

    public boolean isRepairStatus() {
        return repairStatus;
    }

    public void setRepairStatus(boolean repairStatus) {
        this.repairStatus = repairStatus;
    }

    public int getIdModel() {
        return idModel;
    }

    public void setIdModel(int idModel) {
        this.idModel = idModel;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModelType() {
        return modelType;
    }

    public void setModelType(String modelType) {
        this.modelType = modelType;
    }

    public String getFuelType() {
        return fuelType;
    }

    public void setFuelType(String fuelType) {
        this.fuelType = fuelType;
    }

    public int getNoBeds() {
        return noBeds;
    }

    public void setNoBeds(int noBeds) {
        this.noBeds = noBeds;
    }

    public int getPricePerDay() {
        return pricePerDay;
    }

    public void setPricePerDay(int pricePerDay) {
        this.pricePerDay = pricePerDay;
    }
}
