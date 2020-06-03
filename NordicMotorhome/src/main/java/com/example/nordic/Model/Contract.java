/**
 * v
 */
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
    private int bedLinen;
    private int bikeRack;
    private int childSeat;
    private int grill;
    private int chair;
    private int tble;
    private int numberOfBeds; //Needed to be added so @ModelAttribute Contract contract can be used for form post request

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

    public int getBedLinen() {
        return bedLinen;
    }

    public void setBedLinen(int bedLinen) {
        this.bedLinen = bedLinen;
    }

    public int getBikeRack() {
        return bikeRack;
    }

    public void setBikeRack(int bikeRack) {
        this.bikeRack = bikeRack;
    }

    public int getChildSeat() {
        return childSeat;
    }

    public void setChildSeat(int childSeat) {
        this.childSeat = childSeat;
    }

    public int getGrill() {
        return grill;
    }

    public void setGrill(int grill) {
        this.grill = grill;
    }

    public int getChair() {
        return chair;
    }

    public void setChair(int chair) {
        this.chair = chair;
    }

    public int getTble() {
        return tble;
    }

    public void setTble(int tble) {
        this.tble = tble;
    }

    public int getNumberOfBeds() {
        return numberOfBeds;
    }

    public void setNumberOfBeds(int numberOfBeds) {
        this.numberOfBeds = numberOfBeds;
    }
}
