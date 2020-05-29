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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.WebRequest;

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
    public String contractMenuGet(Model model) {
        List<Contract> contractList = contractService.readAll();
        List<Vehicle> vehicleList = vehicleService.fromContracts(contractList);
        List<Customer> customerList = customerService.fromContracts(contractList);

        model.addAttribute("contractList", contractList);
        model.addAttribute("vehicleList", vehicleList);
        model.addAttribute("customerList", customerList);
        return "contract/contractMenu";
    }

    @PostMapping("/contractMenu")
    public String contractMenuPost(WebRequest webRequest, Model model) {
        String searchTerm = webRequest.getParameter("search");
        List<Contract> contractList = contractService.readSearch(searchTerm);
        List<Vehicle> vehicleList = vehicleService.fromContracts(contractList);
        List<Customer> customerList = customerService.fromContracts(contractList);

        model.addAttribute("contractList", contractList);
        model.addAttribute("vehicleList", vehicleList);
        model.addAttribute("customerList", customerList);
        return "contract/contractMenuSearch";

    }

    @GetMapping("/viewDetails/{idContract}")
    public String viewDetailsGet(@PathVariable("idContract") int id, Model model) {
        Contract contract = contractService.findContractById(id);
        Vehicle vehicle = vehicleService.findVehicleById(contract.getIdVehicle());
        Customer customer = customerService.findCustomerByID(contract.getIdCustomer());
        List<Licence> licenceList = licenceService.readFromContractId(id);

        model.addAttribute("contract", contract);
        model.addAttribute("vehicle", vehicle);
        model.addAttribute("customer", customer);
        model.addAttribute("licenceList", licenceList);
        return "contract/viewDetails";
    }

    @GetMapping("/deleteContract/{idContract}")
    public String deleteContractGet(@PathVariable("idContract") int idContract) {
        contractService.deleteContract(idContract);
        return "redirect:/contract/contractMenu";
    }

    @GetMapping("/endContract/{idContract}")
    public String endContractGet(@PathVariable("idContract") int id, Model model) {
        Contract contract = contractService.findContractById(id);
        Vehicle vehicle = vehicleService.findVehicleById(contract.getIdVehicle());
        Customer customer = customerService.findCustomerByID(contract.getIdCustomer());
        List<Licence> licenceList = licenceService.readFromContractId(id);

        model.addAttribute("contract", contract);
        model.addAttribute("vehicle", vehicle);
        model.addAttribute("customer", customer);
        model.addAttribute("licenceList", licenceList);
        return "contract/endContract";
    }


    @GetMapping("/contractCancellation/{idContract}")
    public String contractCancellation(@PathVariable("idContract") int id, WebRequest webRequest, Model model){

        String currentDate = webRequest.getParameter("currentDate");
        String startDate ="${idContract.startDate}";
        Contract contract = contractService.findContractById(id);
        Vehicle vehicle = vehicleService.findVehicleById(contract.getIdVehicle());

        contractService.setWorkingID(id);

        model.addAttribute("contract", contract);
        model.addAttribute("vehicle", vehicle);

        return "contract/contractCancellation";
    }
    /*
    @GetMapping("/contractCancellation2")
    public String contractCancellation(Model model){
        System.out.println("1");
        int id = contractService.getWorkingID();
        Contract contract = contractService.findContractById(id);
        Vehicle vehicle = vehicleService.findVehicleById(contract.getIdVehicle());

        model.addAttribute("contract", contract);
        model.addAttribute("vehicle", vehicle);
        System.out.println("2");
        return "contract/contractCancellation2";
    }

     */

    @PostMapping("/contractCancellation")
    public String contractCancellation(WebRequest webRequest, Model model){

        String currentDate = webRequest.getParameter("currentDate");
        int id = contractService.getWorkingID();
        Contract contract = contractService.findContractById(id);
        Vehicle vehicle = vehicleService.findVehicleById(contract.getIdVehicle());
        model.addAttribute("vehicle", vehicle);
        model.addAttribute("contract", contract);
        double fee = contractService.cancellation(id, currentDate);
        model.addAttribute("fee", fee);
        model.addAttribute("currentDate", currentDate);
        contractService.setWorkingFee(fee);


        return "contract/contractCancellation2";
    }

    @GetMapping("/confirmCancellation")
    public String confirmCancellation() {
        int idContract = contractService.getWorkingID();
        double fee = contractService.getWorkingFee();

        System.out.println("id contract check: " + idContract);
        System.out.println("fee check : " + fee);

        contractService.archiveContract(idContract, fee);
        return "redirect:/contract/contractMenu";
    }

}
