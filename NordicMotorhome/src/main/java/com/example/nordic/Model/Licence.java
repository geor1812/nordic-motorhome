/**
 * Created by Team
 */
package com.example.nordic.Model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.Size;

@Entity
public class Licence {
    @Id
    private int idLicence;
    @Size(max = 10, message = "Licence number is between 0 and 10 digits")
    private String licenceNo;
    @Size(max = 45, message = "First name too long")
    private String firstName;
    @Size(max = 45, message = "Last name too long")
    private String lastName;
    private String birthDate;
    @Size(max = 45, message = "Country name too long")
    private String country;
    private String issueDate;
    private String expiryDate;
    @Size(max = 45, message = "Issuer name too long")
    private String originator;
    @Size(max = 11, message = "Invalid CPR")
    private String cpr;
    private int idContract;

    public int getIdLicence() {
        return idLicence;
    }

    public void setIdLicence(int idLicence) {
        this.idLicence = idLicence;
    }

    public String getLicenceNo() {
        return licenceNo;
    }

    public void setLicenceNo(String licenceNo) {
        this.licenceNo = licenceNo;
    }

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

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(String issueDate) {
        this.issueDate = issueDate;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getOriginator() {
        return originator;
    }

    public void setOriginator(String originator) {
        this.originator = originator;
    }

    public String getCpr() {
        return cpr;
    }

    public void setCpr(String cpr) {
        this.cpr = cpr;
    }

    public int getIdContract() {
        return idContract;
    }

    public void setIdContract(int idContract) {
        this.idContract = idContract;
    }
}
