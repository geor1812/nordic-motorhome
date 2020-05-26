package com.example.nordic.Service;

import com.example.nordic.Model.Customer;
import com.example.nordic.Repository.CustomerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {
    @Autowired
    CustomerRepo customerRepo;

    private int workingId;

    public void createCustomer(Customer customer){customerRepo.createCustomer(customer);}

    public int getWorkingId() {
        return workingId;
    }

    public void setWorkingId(int workingId) {
        this.workingId = workingId;
    }
}