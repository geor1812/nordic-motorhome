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

    /**
     * Created by Remi
     * Link between the controller and the repo for creating customers
     * @param customer customer to be created
     */
    public void createCustomer(Customer customer){customerRepo.createCustomer(customer);}

    /**
     * Created by Remi
     * Link between the controller and the repo for getting the latest customers id
     * @return latest customer id to be added
     */
    public int getLatestCustomerId(){return customerRepo.getLatestCustomerId();}

    /**
     * Created by Team
     * Link between the controller and the repo for finding a customer by id
     * @param id id of the customer to be looked for
     * @return the customer of the given id
     */
    public Customer findCustomerByID(int id) {
        return customerRepo.findCustomerById(id);
    }

    /**
     * Created by George
     * Method to make a list of customers that is directly related to the list of contracts
     * @param contractList list of contracts
     * @return list of customers that is directly related to the list of contracts
     */
    public List<Customer> fromContracts(List<Contract> contractList) {
        List<Customer> customerList = new ArrayList<Customer>();
        for (Contract contract: contractList) {
            customerList.add(customerRepo.findCustomerById(contract.getIdCustomer()));
        }
        return customerList;
    }


    /**
     * Created by Team
     * Link between the controller and the repo for readAll
     * @return list of customers
     */
    public List<Customer> readAll() { return customerRepo.readAll();
    }

    //Getters & Setters
    public int getWorkingId() {
        return workingId;
    }

    public void setWorkingId(int workingId) {
        this.workingId = workingId;
    }
}
