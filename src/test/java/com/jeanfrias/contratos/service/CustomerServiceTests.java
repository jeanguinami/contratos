package com.jeanfrias.contratos.service;

import com.jeanfrias.contratos.bean.request.CustomerRequest;
import com.jeanfrias.contratos.repository.CustomerRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.modules.junit4.PowerMockRunner;


import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

@RunWith(PowerMockRunner.class)
public class CustomerServiceTests {

    @InjectMocks
    CustomerService customerService;

    @Mock
    CustomerRepository customerRepository;

    @Before
    public void before(){

    }

    @Test
    public void createCustomer(){

        //Executing
        customerService.createCustomer(buildCustomerReqDefault());

        //Validating
        verify(customerRepository, times(1)).save(any());
    }

    @Test
    public void createCustomerWithoutSomeData(){

        //Executing
        Exception exception = assertThrows(RuntimeException.class, () ->
                customerService.createCustomer(buildCustomerReqWithoutSomeData()));

        //Validating
        String exceptionMessage = exception.getMessage();

        assertTrue(exceptionMessage.contains("Invalid Customer"));
    }

    @Test
    public void createCustomerAlreadyOnDatabase(){

        //Getting ready
        when(customerRepository.countCnpj(any())).thenReturn(1);

        //Executing
        Exception exception = assertThrows(RuntimeException.class, () ->
                customerService.createCustomer(buildCustomerReqDefault()));

        //Validating

        String exceptionMessage = exception.getMessage();

        assertTrue(exceptionMessage.contains("Customer already on database"));
    }

    private CustomerRequest buildCustomerReqDefault() {

        return CustomerRequest.builder()
                .customerId("1")
                .customerName("Marcos Júnior")
                .customerCnpj("12345678910")
                .build();
    }

    private CustomerRequest buildCustomerReqWithoutSomeData() {

        return CustomerRequest.builder()
                .customerId("1")
                .customerCnpj("12345678910")
                .build();
    }
}
