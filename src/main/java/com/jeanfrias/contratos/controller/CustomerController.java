package com.jeanfrias.contratos.controller;

import com.jeanfrias.contratos.bean.request.CustomerRequest;
import com.jeanfrias.contratos.bean.response.CustomerResponse;
import com.jeanfrias.contratos.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    @Autowired
    CustomerService customerService;

    @GetMapping("/filter/{n}")
    public ArrayList<CustomerResponse> getCustomersByName(
            @PathVariable("n") String customerName,
            @RequestParam(value = "offset", defaultValue = "0") Integer offset,
            @RequestParam(value = "limit", defaultValue = "10") Integer limit){
        return customerService.getCustomersByName(customerName, offset, limit);
    }

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public void createCustomer(@RequestBody @Valid CustomerRequest request){
        customerService.createCustomer(request);
    }

    @PutMapping
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void updateCustomer(@RequestBody @Valid CustomerRequest request){
        customerService.updateCustomer(request);
    }

    @DeleteMapping("/remove/{c}")
    public void removeCustomer (@PathVariable("c") String customerId){
        customerService.deleteCustomer(customerId);
    }
}
