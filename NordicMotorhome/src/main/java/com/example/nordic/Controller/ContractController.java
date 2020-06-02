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

    /**
     * Get request for the contract menu page
     * @param model used to pass the information from the controller to the view
     * @return contractMenu view
     */
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

    /**
     * Post method which gets the search term inserted by the user and retrieves
     * a list of contracts filtered down to contain the search term
     * @param webRequest used to retrieve the input from the view
     * @param model used to pass the information from the controller to the view
     * @return contractMenuSearch page
     */
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

    /**
     * Get request for the view details on contract page
     * @param id the id of the contract to be viewed
     * @param model used to pass the information from the controller to the view
     * @return viewDetails page
     */
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

    /**
     * Get request for the update contract page
     * @param idContract the id of the contract to be updated
     * @param model used to pass the information from the controller to the view
     * @return updateContract page
     */
    @GetMapping("/updateContract/{idContract}")
    public String updateContractGet(@PathVariable("idContract") int idContract, Model model) {
        contractService.setWorkingID(idContract);
        List<Licence> licenceList = licenceService.readFromContractId(idContract);

        model.addAttribute("contract", contractService.findContractById(idContract));
        model.addAttribute("licenceList", licenceList);
        return "contract/updateContract";
    }

    /**
     * Post method which gets the updated information
     * @param contract the contract to be updated
     * @return contractMenu page
     */
    @PostMapping("/updateContract")
    public String updateContractPost(@ModelAttribute Contract contract){
        int id = contractService.getWorkingID();
        contractService.updateContract(id, contract);
        return "redirect:/contract/contractMenu";
    }

    /**
     * Get request for the delete contract page
     * @param idContract the id of the contract to be deleted.
     * @return contractMenu page
     */
    @GetMapping("/deleteContract/{idContract}")
    public String deleteContractGet(@PathVariable("idContract") int idContract) {
        contractService.deleteContract(idContract);
        return "redirect:/contract/contractMenu";
    }

    /**
     * Get request for the choose customer in create contract
     * @param model used to pass the information from the controller to the view
     * @return createChooseCustomer page
     */
    @GetMapping("/createChooseCustomer")
    public String createChooseCustomerGet(Model model) {
        List<Customer> customerList = customerService.readAll();
        model.addAttribute("customerList", customerList);
        return "contract/createChooseCustomer";
    }


    /**
     * Get request for "select dates" page
     * @param idCustomer the customer for which the date applies
     * @return selectDates page
     */
    @GetMapping("/selectDates/{idCustomer}")
    public String selectDates(@PathVariable("idCustomer") int idCustomer){
        customerService.setWorkingId(idCustomer);
        return "contract/selectDates";
    }

    /**
     * Post method for "select dates"
     * @param contract the contract for which the dates applies
     * @param model used to pass the information from the controller to the view
     * @return availableVehicles page
     */
    @PostMapping("/selectDates")
    public String availableVehicles(@ModelAttribute Contract contract, Model model){
        //had to add numberOfBeds to contract so @ModelAttribute could be used
        List<Vehicle> listDates = contractService.vehiclesFromContractList(contract.getNumberOfBeds(), contract.getStartDate(),
        contract.getEndDate());
        contractService.setStartDate(contract.getStartDate());
        contractService.setEndDate(contract.getEndDate());
        model.addAttribute("vehicles", listDates);
        return "vehicle/availableVehicles";
    }

    /**
     * Get request for adding accessories to vehicle
     * @param idVehicle vehicle to which the accessories are addded
     * @param model used to pass the information from the controller to the view
     * @return addAccessories page
     */
    @GetMapping("/addAccessories/{idVehicle}")
    public String addAccessoriesGet(@PathVariable("idVehicle") int idVehicle,Model model){
        vehicleService.setWorkingID(idVehicle);
        return "/contract/addAccessories";
    }

    /**
     * Post method for adding accessories
     * @param contract contract to which the accessories are to be added
     * @return createLicence page
     */
    @PostMapping("/addAccessories")
    public String addAccessoriesPost(@ModelAttribute Contract contract){
        contract.setIdCustomer(customerService.getWorkingId());
        contract.setIdVehicle(vehicleService.getWorkingID());
        contract.setStartDate(contractService.getStartDate());
        contract.setEndDate(contractService.getEndDate());
        String idContract = contractService.createContract(contract);
        return "redirect:/contract/createLicence/" + idContract;
    }

    /**
     * Get request for creating licence page
     * @param idContract contract to which the licence is connected
     * @param model used to pass the information from the controller to the view
     * @return createLicence page
     */
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

    /**
     * Post method for creating licences
     * @param licence to be created
     * @return finaliseContract page
     */
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

    /**
     * Get request for finalising contracts
     * @param idLicence licence of the contract to be finalized
     * @param model used to pass the information from the controller to the view
     * @return finaliseContract page
     */
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

    /**
     * Get request for approving contracts
     * @return contractMenu page
     */
    @GetMapping("/contractApproved")
    public String contractApprovedGet(){
        return "redirect:/contract/contractMenu";
    }

    /**
     * Get request for non approved contracts
     * @return contractMenu page
     */
    @GetMapping("/contractNotApproved")
    public String contractNotApprovedGet(){
        contractService.deleteContract(contractService.getWorkingID());
        return "redirect:/contract/contractMenu";
    }

    /**
     * Get request for ending contracts
     * @param id id of the contract that is to be ended
     * @param model used to pass the information from the controller to the view
     * @return endContract page
     */
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

    /**
     * Get request for checking out contracts
     * @param id id of the contract that is to be checked out
     * @return contractCheckout page
     */
    @GetMapping("/contractCheckout/{idContract}")
    public String contractCheckoutGet(@PathVariable("idContract") int id) {
        contractService.setWorkingID(id);
        return "contract/contractCheckout";
    }

    /**
     * Post method for checking out contracts
     * @param webRequest used to retrieve the input from the view
     * @param model used to pass the information from the controller to the view
     * @return contractCheckout2 page
     */
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
            model.addAttribute("fuelCharge", 70);
        } else {
            model.addAttribute("fuelCharge", 0);
        }


        contractService.setWorkingFuelCharge(fuelCharge);
        contractService.setWorkingFee(totalPrice);

        return "contract/contractCheckout2";
    }

    /**
     * Get request for cancelling contracts
     * @param id id of the contract that is to be cancelled
     * @param webRequest used to retrieve the input from the view
     * @param model used to pass the information from the controller to the view
     * @return contractCancellation page
     */
    @GetMapping("/contractCancellation/{idContract}")
    public String contractCancellationGet(@PathVariable("idContract") int id, WebRequest webRequest, Model model){

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

    /**
     * Post method for cancelling contracts
     * @param webRequest used to retrieve the input from the view
     * @param model used to pass the information from the controller to the view
     * @return contractCancellation2 page
     */
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

    /**
     * Get request for confirming the cancellation
     * @return to the contract menu
     */
    @GetMapping("/confirmCancellation")
    public String confirmCancellation() {
        int idContract = contractService.getWorkingID();
        double fee = contractService.getWorkingFee();

        contractService.archiveContract(idContract, fee, 0, 0, false);
        return "redirect:/contract/contractMenu";
    }

    /**
     * Get request for the confirmation of the checkout
     * @return to the contract menu
     */
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
