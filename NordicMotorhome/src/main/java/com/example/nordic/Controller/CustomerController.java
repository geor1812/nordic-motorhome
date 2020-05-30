package com.example.nordic.Controller;

import com.example.nordic.Model.Customer;
import com.example.nordic.Service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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
    public String createCustomerGet() {
        return "customer/createCustomer";}

    /* Post method which creates a customer object and sends it to customerService

     */
    @PostMapping("/createCustomer")
    public String createCustomerPost(@ModelAttribute Customer customer){
        customerService.createCustomer(customer);
        int idCustomer = customerService.getLatestCustomerId();
        return "redirect:/contract/selectDates/" + idCustomer;
    }


}
