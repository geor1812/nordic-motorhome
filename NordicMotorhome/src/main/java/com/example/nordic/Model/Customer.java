package com.example.nordic.Model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Customer extends Person {

    @Id
    private int idCustomer;

    public int getIdCustomer() {
        return idCustomer;
    }

    public void setIdCustomer(int idCustomer) {
        this.idCustomer = idCustomer;
    }

}
