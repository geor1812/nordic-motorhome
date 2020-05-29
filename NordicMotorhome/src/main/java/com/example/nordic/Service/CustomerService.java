package com.example.nordic.Service;

import com.example.nordic.Model.Contract;
import com.example.nordic.Model.Customer;
import com.example.nordic.Model.Vehicle;
import com.example.nordic.Repository.CustomerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomerService {
    @Autowired
    CustomerRepo customerRepo;

    private int workingId;

    public void createCustomer(Customer customer){customerRepo.createCustomer(customer);}

    public int getLatestCustomerId(){return customerRepo.getLatestCustomerId();}

    public Customer findCustomerByID(int id) {
        return customerRepo.findCustomerById(id);
    }

    public List<Customer> fromContracts(List<Contract> contractList) {
        List<Customer> customerList = new ArrayList<Customer>();
        for (Contract contract: contractList) {
            customerList.add(customerRepo.findCustomerById(contract.getIdCustomer()));
        }
        return customerList;
    }

    public int getWorkingId() {
        return workingId;
    }

    public void setWorkingId(int workingId) {
        this.workingId = workingId;
    }
}
