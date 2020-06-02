package com.example.nordic.Controller;

import com.example.nordic.Model.Customer;
import com.example.nordic.Service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    CustomerService customerService;

    /**
     * Get request for create customer page
     * @return the create customer page
     */
    @GetMapping("/createCustomer")
    public String createCustomerGet(Customer customer) {
        return "customer/createCustomer";}

    /**
     * Post method which creates a customer object and sends it to customerService
     * @param customer customer to be send to the customerService
     * @return selectDates page
     */
    @PostMapping("/createCustomer")
    public String createCustomerPost(@ModelAttribute @Valid Customer customer, BindingResult bindingResult){
        if(bindingResult.hasErrors()) {
            return "customer/createCustomer";
        } else {
            customerService.createCustomer(customer);
            int idCustomer = customerService.getLatestCustomerId();
            return "redirect:/contract/selectDates/" + idCustomer;
        }
    }


}
