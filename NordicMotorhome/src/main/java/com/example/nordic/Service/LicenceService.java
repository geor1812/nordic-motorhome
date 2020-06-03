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

    /**
     * Created by Remi & George
     * Link between the controller and the repo for reading contract from id
     * @param id
     * @return
     */
    public List<Licence> readFromContractId(int id) {
        return licenceRepo.readFromContractId(id);
    }

    /**
     * Created by Remi
     * Link between the controller and the repo for creating a licence
     * @param licence licence to be created
     * @return result from repo
     */
    public String createLicence(Licence licence) {
        return licenceRepo.createLicence(licence);
    }

    /**
     * Created by Team
     * Link between the controller and the repo for finding licence by its id
     * @param idLicence id of the licence to be looked for
     * @return licence of the given id
     */
    public Licence findLicenceById(int idLicence) {return licenceRepo.findLicenceById(idLicence);
    }

    //Getters and setters
    public int getWorkingId() {
        return workingId;
    }

    public void setWorkingId(int workingId) {
        this.workingId = workingId;
    }
}
