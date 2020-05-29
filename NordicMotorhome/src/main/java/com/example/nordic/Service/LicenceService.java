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

    private int workingId;

    public List<Licence> readFromContractId(int id) {
        return licenceRepo.readFromContractId(id);
    }

    public int getWorkingId() {
        return workingId;
    }

    public void setWorkingId(int workingId) {
        this.workingId = workingId;
    }

    public String createLicence(Licence licence) { return licenceRepo.createLicence(licence);
    }

    public Licence findLicenceById(int idLicence) {return licenceRepo.findLicenceById(idLicence);
    }
}
