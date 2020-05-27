package com.example.nordic.Service;

import com.example.nordic.Model.Licence;
import com.example.nordic.Repository.LicenceRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LicenceService {
    @Autowired
    LicenceRepo licenceRepo;

    public List<Licence> readFromContractId(int id) {
        return licenceRepo.readFromContractId(id);
    }
}
