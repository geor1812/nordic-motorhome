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
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.thymeleaf.util.ListUtils;

import javax.validation.Valid;
import java.util.Collection;
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
        List<Licence> licenceList = licenceService.readFromContractId(idContract);

        model.addAttribute("contract", contractService.findContractById(idContract));
        model.addAttribute("licenceList", licenceList);
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

    @GetMapping("/createChooseCustomer")
    public String createChooseCustomerGet(Model model) {
        List<Customer> customerList = customerService.readAll();
        model.addAttribute("customerList", customerList);
        return "contract/createChooseCustomer";
    }


    /* Get request for "select dates" page
     */
    @GetMapping("/selectDates/{idCustomer}")
    public String selectDates(@PathVariable("idCustomer") int idCustomer){
        customerService.setWorkingId(idCustomer);
        return "contract/selectDates";
    }
    /* post request for "select dates"
     */
    @PostMapping("/selectDates")
    public String availableVehicles(@ModelAttribute Contract contract, Model model){
        //had to add numberOfBeds to contract so @ModelAttribute could be used
        List<Vehicle> listDates = contractService.vehiclesFromContractList(contract.getStartDate(), contract.getEndDate(),
        contract.getNumberOfBeds());
        contractService.setStartDate(contract.getStartDate());
        contractService.setEndDate(contract.getEndDate());
        model.addAttribute("vehicles", listDates);
        return "vehicle/availableVehicles";
    }


    @GetMapping("/addAccessories/{idVehicle}")
    public String addAccessoriesGet(@PathVariable("idVehicle") int idVehicle,Model model){
        vehicleService.setWorkingID(idVehicle);
        return "/contract/addAccessories";
    }

    @PostMapping("/addAccessories")
    public String addAccessoriesPost(@ModelAttribute Contract contract){
        contract.setIdCustomer(customerService.getWorkingId());
        contract.setIdVehicle(vehicleService.getWorkingID());
        contract.setStartDate(contractService.getStartDate());
        contract.setEndDate(contractService.getEndDate());
        String idContract = contractService.createContract(contract);
        return "redirect:/contract/createLicence/" + idContract;
    }

    @GetMapping("/createLicence/{idContract}")
    public String createLicenceGet(@PathVariable("idContract") int idContract, Model model, Licence licence){
        contractService.setWorkingID(idContract);
        return "/contract/createLicence";
    }

    @GetMapping("/createLicence2/{idContract}")
    public String createLicence2Get(@PathVariable("idContract") int idContract, Model model, Licence licence){
        contractService.setWorkingID(idContract);
        return "/contract/createLicence2";
    }

    @PostMapping("/createLicence")
    public String createLicencePost(@ModelAttribute @Valid Licence licence, BindingResult bindingResult){
        if(bindingResult.hasErrors()) {
            return "contract/createLicence";
        } else {
            licence.setIdContract(contractService.getWorkingID());
            String idLicence = licenceService.createLicence(licence);
            return "redirect:/contract/finaliseContract/" +  idLicence;
        }
    }

    @PostMapping("/createLicence2")
    public String createLicence2Post(@ModelAttribute @Valid Licence licence, BindingResult bindingResult){
        if(bindingResult.hasErrors()) {
            return "contract/createLicence2";
        } else {
            licence.setIdContract(contractService.getWorkingID());
            String idLicence = licenceService.createLicence(licence);
            return "redirect:/contract/contractMenu";
        }
    }

    @GetMapping("/finaliseContract/{idLicence}")
    public String finaliseContractGet(@PathVariable("idLicence") int idLicence, Model model){
        licenceService.setWorkingId(idLicence);
        Vehicle vehicle = vehicleService.findVehicleById(vehicleService.getWorkingID());
        Customer customer = customerService.findCustomerByID(customerService.getWorkingId());
        Contract contract = contractService.findContractById(contractService.getWorkingID());
        Licence licence = licenceService.findLicenceById(idLicence);
        model.addAttribute("vehicle", vehicle);
        model.addAttribute("customer", customer);
        model.addAttribute("contract", contract);
        model.addAttribute("licence", licence);
        return "/contract/finaliseContract";
    }


    @GetMapping("/contractApproved")
    public String contractApprovedGet(){
        return "redirect:/contract/contractMenu";
    }

    @GetMapping("/contractNotApproved")
    public String contractNotApprovedGet(){
        contractService.deleteContract(contractService.getWorkingID());
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
        model.addAttribute("totalPrice", totalPrice);
        model.addAttribute("standardPrice", contractService.totalPrice(id));
        model.addAttribute("odometerCharge", contractService.getWorkingOdometerCharge());
        model.addAttribute("pickUpCharge", contractService.getWorkingPickUpCharge());
        if(fuelCharge) {
            model.addAttribute("fuelCharge", 50);
        } else {
            model.addAttribute("fuelCharge", 0);
        }


        contractService.setWorkingFuelCharge(fuelCharge);
        contractService.setWorkingFee(totalPrice);

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
        Customer customer = customerService.findCustomerByID(contract.getIdCustomer());

        model.addAttribute("vehicle", vehicle);
        model.addAttribute("contract", contract);
        model.addAttribute("customer", customer);
        model.addAttribute("fee", fee);
        model.addAttribute("currentDate", currentDate);

        contractService.setWorkingFee(fee);

        return "contract/contractCancellation2";
    }

    @GetMapping("/confirmCancellation")
    public String confirmCancellation() {
        int idContract = contractService.getWorkingID();
        double fee = contractService.getWorkingFee();

        contractService.archiveContract(idContract, fee, 0, 0, false);
        return "redirect:/contract/contractMenu";
    }

    @GetMapping("/confirmCheckout")
    public String confirmCheckout() {
        int id = contractService.getWorkingID();
        double price = contractService.getWorkingFee();
        double odometerCharge = contractService.getWorkingOdometerCharge();
        double pickUpCharge = contractService.getWorkingPickUpCharge();
        boolean fuelCharge = contractService.isWorkingFuelCharge();

        contractService.archiveContract(id, price, odometerCharge, pickUpCharge, fuelCharge);

        return "redirect:/contract/contractMenu";
    }
}
