package com.example.nordic.Controller;

import com.example.nordic.Model.Contract;
import com.example.nordic.Model.Customer;
import com.example.nordic.Model.Licence;
import com.example.nordic.Model.Vehicle;
import com.example.nordic.Service.ContractService;
import com.example.nordic.Service.CustomerService;
import com.example.nordic.Service.LicenceService;
import com.example.nordic.Service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/contract")
public class ContractController {
    @Autowired
    ContractService contractService;
    @Autowired
    VehicleService vehicleService;
    @Autowired
    CustomerService customerService;
    @Autowired
    LicenceService licenceService;

    @GetMapping("/contractMenu")
    public String contractMenuGet(Model contractModel, Model vehicleModel,
                                  Model customerModel) {
        List<Contract> contractList = contractService.readAll();
        List<Vehicle> vehicleList = vehicleService.fromContracts(contractList);
        List<Customer> customerList = customerService.fromContracts(contractList);

        contractModel.addAttribute("contractList", contractList);
        vehicleModel.addAttribute("vehicleList", vehicleList);
        customerModel.addAttribute("customerList", customerList);
        return "contract/contractMenu";
    }

    @GetMapping("/viewDetails/{idContract}")
    public String viewDetailsGet(@PathVariable("idContract") int id, Model contractModel,
                                 Model vehicleModel, Model customerModel, Model licenceModel) {
        Contract contract = contractService.findContractById(id);
        Vehicle vehicle = vehicleService.findVehicleById(contract.getIdVehicle());
        Customer customer = customerService.findCustomerByID(contract.getIdCustomer());
        List<Licence> licenceList = licenceService.readFromContractId(id);

        contractModel.addAttribute("contract", contract);
        vehicleModel.addAttribute("vehicle", vehicle);
        customerModel.addAttribute("customer", customer);
        licenceModel.addAttribute("licenceList", licenceList);
        return "contract/viewDetails";
    }

    @GetMapping("/updateContract/{idContract}")
    public String updateContractGet(@PathVariable("idContract") int idContract, Model model) {
        contractService.setWorkingID(idContract);
        model.addAttribute("contract", contractService.findContractById(idContract));
        return "contract/updateContract";
    }

    @PostMapping("/updateContract")
    public String updateContractPost(@ModelAttribute Contract contract){
        int id = contractService.getWorkingID();
        contractService.updateContract(id, contract);
        return "redirect:/contract/contractMenu";
    }

    @GetMapping("/deleteContract/{idContract}")
    public String deleteContractGet(@PathVariable("idContract") int idContract) {
        contractService.deleteContract(idContract);
        return "redirect:/contract/contractMenu";
    }
}
