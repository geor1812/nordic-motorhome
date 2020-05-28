package com.example.nordic.Service;

import com.example.nordic.Model.Contract;
import com.example.nordic.Model.Vehicle;
import com.example.nordic.Repository.ContractRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContractService {
    @Autowired
    ContractRepo contractRepo;
    private int workingID;

    public List<Contract> readAll() {
        return contractRepo.readAll();
    }

    public List<Contract> readSearch(String search) {
        return contractRepo.readSearch(search);
    }

    public Contract findContractById(int id) {
        return contractRepo.findContractById(id);
    }

    public void updateContract(int id, Contract contract) {
        contractRepo.updateContract(id, contract);
    }

    public int getWorkingID() {
        return workingID;
    }

    public void setWorkingID(int workingID) {
        this.workingID = workingID;
    }

    public void deleteContract(int idContract) {
        contractRepo.deleteContract(idContract);
    }
}