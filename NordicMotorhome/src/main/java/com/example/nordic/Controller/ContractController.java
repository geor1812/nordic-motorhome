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

    /* Get request for "select dates" page
     */
    @GetMapping("/selectDates/{idCustomer}")
    public String createContractGet(@PathVariable("idCustomer") int idCustomer){
        customerService.setWorkingId(idCustomer);
        return "contract/selectDates";
    }
    /* post request for "select dates"
     */
    @PostMapping("/selectDates")
    public String availableVehicles(@ModelAttribute Contract contract, Model model){
        //had to add numberOfBeds to contract so @ModelAttribute could be used
        List<Vehicle> list = vehicleService.availableVehiclesList(contract.getNumberOfBeds());
        contractService.setStartDate(contract.getStartDate());
        contractService.setEndDate(contract.getEndDate());
        model.addAttribute("vehicles", list);
        return "vehicle/availableVehicles";
    }

    @GetMapping("/finaliseContract/{idVehicle}")
    public String finaliseContractGet(@PathVariable("idVehicle") int idVehicle,Model model){
        Vehicle vehicle = vehicleService.findVehicleById(idVehicle);
        vehicleService.setWorkingID(idVehicle);
        Customer customer = customerService.findCustomerByID(customerService.getWorkingId());
        model.addAttribute("vehicle", vehicle);
        model.addAttribute("customer", customer);

        return "/contract/finaliseContract";
    }

    @PostMapping("/finaliseContract")
    public String finaliseContractPost(){
        /*
        int idCustomer = customerService.getWorkingId();
        int idVehicle = vehicleService.getWorkingID();
        String startDate = contractService.getStartDate();
        String endDate = contractService.getEndDate();
        Contract contract = new Contract();
        contract.setIdCustomer(idCustomer);
        contract.setIdVehicle(idVehicle);
        contract.setStartDate(startDate);
        contract.setEndDate(endDate);
        contractService.createContract(contract);

         */
        return "/contract/addAccessories";
    }

    @PostMapping("/addAccessories")
    public String addAccessoriesPost(@ModelAttribute Contract contract){

        int idCustomer = customerService.getWorkingId();
        int idVehicle = vehicleService.getWorkingID();
        String startDate = contractService.getStartDate();
        String endDate = contractService.getEndDate();
        contract.setIdCustomer(idCustomer);
        contract.setIdVehicle(idVehicle);
        contract.setStartDate(startDate);
        contract.setEndDate(endDate);
        contractService.createContract(contract);

        return "redirect:/";

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

    @GetMapping("/contractCheckout/{idContract}")
    public String contractCheckoutGet(@PathVariable("idContract") int id) {
        contractService.setWorkingID(id);
        return "contract/contractCheckout";
    }

    @PostMapping("/contractCheckout")
    public String contractCheckoutPost(WebRequest webRequest, Model model) {
        int endOdometer = Integer.valueOf(webRequest.getParameter("endOdometer"));
        int pickUpKm = Integer.valueOf(webRequest.getParameter("pickUpKm"));
        boolean fuelCharge = Boolean.valueOf(webRequest.getParameter("fuelCharge"));
        int id = contractService.getWorkingID();
        double totalPrice = contractService.checkout(id, endOdometer, pickUpKm, fuelCharge);

        Contract contract = contractService.findContractById(id);
        Vehicle vehicle = vehicleService.findVehicleById(contract.getIdVehicle());
        Customer customer = customerService.findCustomerByID(contract.getIdCustomer());


        model.addAttribute("contract", contract);
        model.addAttribute("vehicle", vehicle);
        model.addAttribute("customer", customer);

        return "contract/contractCheckout2";
    }

    @GetMapping("/contractCancellation/{idContract}")
    public String contractCancellation(@PathVariable("idContract") int id, WebRequest webRequest, Model model){

        String currentDate = webRequest.getParameter("currentDate");
        String startDate ="${idContract.startDate}";
        Contract contract = contractService.findContractById(id);
        Vehicle vehicle = vehicleService.findVehicleById(contract.getIdVehicle());
        Customer customer = customerService.findCustomerByID(contract.getIdCustomer());
        contractService.setWorkingID(id);

        model.addAttribute("contract", contract);
        model.addAttribute("vehicle", vehicle);
        model.addAttribute("customer", customer);
        return "contract/contractCancellation";
    }

    @PostMapping("/contractCancellation")
    public String contractCancellationPost(WebRequest webRequest, Model model){

        String currentDate = webRequest.getParameter("currentDate");
        int id = contractService.getWorkingID();
        double fee = contractService.cancellation(id, currentDate);
        Contract contract = contractService.findContractById(id);
        Vehicle vehicle = vehicleService.findVehicleById(contract.getIdVehicle());

        model.addAttribute("vehicle", vehicle);
        model.addAttribute("contract", contract);
        model.addAttribute("fee", fee);
        model.addAttribute("currentDate", currentDate);

        contractService.setWorkingFee(fee);

        return "contract/contractCancellation2";
    }

    @GetMapping("/confirmCancellation")
    public String confirmCancellation() {
        int idContract = contractService.getWorkingID();
        double fee = contractService.getWorkingFee();

        contractService.archiveContract(idContract, fee);
        return "redirect:/contract/contractMenu";
    }

    @GetMapping("/confirmCheckout")
    public  String confirmCheckout() {
        return "redirect:/contract/contractMenu";
    }

}
