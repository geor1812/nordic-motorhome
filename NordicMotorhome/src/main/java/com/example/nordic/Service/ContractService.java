package com.example.nordic.Service;

import com.example.nordic.Model.Contract;
import com.example.nordic.Repository.ContractRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContractService {
    @Autowired
    ContractRepo contractRepo;

    public List<Contract> readAll() {
        return contractRepo.readAll();
    }

    public Contract findContractById(int id){
        return contractRepo.findContractById(id);
    }
}
