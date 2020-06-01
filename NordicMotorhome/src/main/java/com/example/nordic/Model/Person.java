package com.example.nordic.Model;

import javax.validation.constraints.Size;

public class Person {
    @Size(max = 45, message = "First name too long")
    private String firstName;
    @Size(max = 45, message = "Last name too long")
    private String lastName;
    @Size(max = 16, message = "Invalid phone number")
    private String phoneNo;
    @Size(max = 45, message = "Invalid email address")
    private String email;
    private int idAddress;
    @Size(max = 45, message = "Address too long")
    private String addressDetails;
    @Size(max = 45, message = "City name too long")
    private String city;
    @Size(max = 45, message = "Country name too long")
    private String country;
    @Size(max = 45, message = "State name too long")
    private String state;
    @Size(max = 10, message = "Invalid zip")
    private String zip;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getIdAddress() {
        return idAddress;
    }

    public void setIdAddress(int idAddress) {
        this.idAddress = idAddress;
    }

    public String getAddressDetails() {
        return addressDetails;
    }

    public void setAddressDetails(String addressDetails) {
        this.addressDetails = addressDetails;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }
}
