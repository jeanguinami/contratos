package com.jeanfrias.contratos.service;

import com.jeanfrias.contratos.bean.ejb.Customer;
import com.jeanfrias.contratos.bean.request.CustomerRequest;
import com.jeanfrias.contratos.bean.response.CustomerResponse;
import com.jeanfrias.contratos.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

import static org.apache.commons.lang3.StringUtils.isBlank;

@Service
public class CustomerService {

    @Autowired
    CustomerRepository customerRepository;

    public ArrayList<CustomerResponse> getCustomersByName(String customerName, Integer offset, Integer limit) {
        Pageable pagination = PageRequest.of(offset, limit, Sort.unsorted());
        ArrayList<CustomerResponse> customerResponseList = new ArrayList<>();

        Page<Customer> customers = customerRepository.findByCustomerName(customerName, pagination);
        customers.forEach((c) -> customerResponseList.add(buildCustomerResponse(c)));

        return customerResponseList;
    }

    public void createCustomer(CustomerRequest customerReq) {
        Customer customer = buildCustomer(customerReq);
        customerRepository.save(customer);
    }

    public void updateCustomer(CustomerRequest customerReq) {
        Customer customer = customerRepository.findById(Long.valueOf(customerReq.getCustomerId()))
                .orElseThrow(() -> new RuntimeException("Invalid Customer"));

        customer.setName(customerReq.getCustomerName());
        customer.setCnpj(customerReq.getCustomerCnpj());

        customerRepository.save(customer);
    }

    public void deleteCustomer(String customerId) {
        Customer customer = customerRepository.findById(Long.valueOf(customerId))
                .orElseThrow(() -> new RuntimeException("Customer isn't on database"));
        customerRepository.delete(customer);
    }

    private Customer buildCustomer(CustomerRequest customerReq){
        Integer findCount = customerRepository.countCnpj(customerReq.getCustomerCnpj());
        if(findCount == 0) {
            if(!isBlank(customerReq.getCustomerName()) && !isBlank(customerReq.getCustomerCnpj())){

                return Customer.builder()
                        .name(customerReq.getCustomerName())
                        .cnpj(customerReq.getCustomerCnpj())
                        .build();

            } else {
                throw new RuntimeException("Invalid Customer");
            }
        } else {
            throw new RuntimeException("Customer already on database");
        }
    }

    private CustomerResponse buildCustomerResponse(Customer customer) {

        return CustomerResponse.builder()
                .customerId(String.valueOf(customer.getId()))
                .customerCnpj(customer.getCnpj())
                .customerName(customer.getName())
                .build();
    }
}