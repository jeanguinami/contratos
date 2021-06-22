package com.jeanfrias.contratos.service;

import com.jeanfrias.contratos.bean.ejb.Contract;
import com.jeanfrias.contratos.bean.ejb.Customer;
import com.jeanfrias.contratos.bean.ejb.Job;
import com.jeanfrias.contratos.bean.request.ContractRequest;
import com.jeanfrias.contratos.bean.response.ContractResponse;
import com.jeanfrias.contratos.repository.ContractRepository;
import com.jeanfrias.contratos.repository.CustomerRepository;
import com.jeanfrias.contratos.repository.JobRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.data.domain.PageImpl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@RunWith(PowerMockRunner.class)
public class ContractServiceTest {

    @InjectMocks
    ContractService contractService;

    @Mock
    ContractRepository contractRepository;

    @Mock
    CustomerRepository customerRepository;

    @Mock
    JobRepository jobRepository;

    @Before
    public void before() {

    }

    @Test
    public void createContract(){

        //Getting ready
        when(customerRepository.findById(any())).thenReturn(buildCustomerDefault());
        when(jobRepository.findById(any())).thenReturn(buildJobDefault());

        //Executing
        contractService.createContract(buildContractReqDefault());

        //Validating
        verify(contractRepository, times(1)).save(any());
    }

    @Test
    public void createContractWithoutSomeData(){

        //Getting ready
        when(customerRepository.findById(any())).thenReturn(buildCustomerDefault());
        when(jobRepository.findById(any())).thenReturn(buildJobDefault());

        //Executing
        Exception exception = assertThrows(RuntimeException.class, () ->
                contractService.createContract(buildContractReqWithoutSomeData()));

        //Validating
        String exceptionMessage = exception.getMessage();

        assertTrue(exceptionMessage.contains("Invalid Contract"));
    }

    @Test
    public void createContractInvalidDate(){

        //Getting ready
        when(customerRepository.findById(any())).thenReturn(buildCustomerDefault());
        when(jobRepository.findById(any())).thenReturn(buildJobDefault());

        //Executing
        Exception exception = assertThrows(RuntimeException.class, () ->
                contractService.createContract(buildContractReqInvalidDate()));

        //Validating
        String exceptionMessage = exception.getMessage();

        assertTrue(exceptionMessage.contains("Invalid Date"));
    }

    @Test
    public void updateContract(){

        //Getting ready
        when(contractRepository.findById(any())).thenReturn(buildContractDefault());
        when(customerRepository.findById(any())).thenReturn(buildCustomerDefault());
        when(jobRepository.findById(any())).thenReturn(buildJobDefault());

        //Executing
        contractService.updateContract(buildContractReqDefault());

        //Validating
        verify(contractRepository, times(1)).save(any());
    }

    @Test
    public void deleteContract(){

        //Getting ready
        when(contractRepository.findById(any())).thenReturn(buildContractDefault());

        //Executing
        contractService.deleteContract("1");

        //Validating
        verify(contractRepository, times(1)).delete(any());
    }

    @Test
    public void getContractByCustomerCnpj(){

        //Getting ready
        when(contractRepository.findByCustomerCnpj(any(), any()))
                .thenReturn(new PageImpl<>(buildContractListDefault()));

        //Executing
        ArrayList<ContractResponse> contractResponseList = contractService.
                getContractsByCustomerCnpj("12345678910", 0, 10);

        //Validating
        assertNotNull(contractResponseList);
    }

    private ContractRequest buildContractReqDefault() {

        return ContractRequest.builder()
                .contractId("1")
                .customerId("1")
                .jobId("1")
                .endOfContract("12/10/2022")
                .build();
    }

    private ContractRequest buildContractReqInvalidDate() {

        return ContractRequest.builder()
                .contractId("1")
                .customerId("1")
                .jobId("1")
                .endOfContract("a12/10/2022")
                .build();
    }

    private ContractRequest buildContractReqWithoutSomeData() {

        return ContractRequest.builder()
                .contractId("1")
                .customerId("1")
                .endOfContract("12/10/2022")
                .build();
    }

    private Optional<Contract> buildContractDefault() {

        return Optional.of(Contract.builder()
                .id(1L)
                .customer(new Customer(1L, "Carlos Meyers", "12345678910"))
                .job(new Job(1L, "Support"))
                .endOfContract(convertStringToDate("12/10/2022"))
                .build());
    }

    private Contract buildContractListADefault() {

        return Contract.builder()
                .id(1L)
                .customer(new Customer(1L, "Rodrigo Meyers", "12345678910"))
                .job(new Job(1L, "Development"))
                .endOfContract(convertStringToDate("12/10/2023"))
                .build();
    }

    private Contract buildContractListBDefault() {

        return Contract.builder()
                .id(1L)
                .customer(new Customer(1L, "Maria da Penha", "12345671019"))
                .job(new Job(1L, "Support"))
                .endOfContract(convertStringToDate("12/10/2024"))
                .build();
    }

    private List<Contract> buildContractListDefault(){
        List<Contract> contracts = new ArrayList<>();
        contracts.add(buildContractListADefault());
        contracts.add(buildContractListBDefault());
        return contracts;
    }
    private Optional<Job> buildJobDefault() {

        return Optional.of(Job.builder()
                .id(1L)
                .name("Support")
                .build());
    }

    private Optional<Customer> buildCustomerDefault() {

        return Optional.of(Customer.builder()
                .id(1L)
                .name("Marcos Júnior")
                .cnpj("12345678910")
                .build());
    }

    private static Date convertStringToDate(String convert) {

        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");

        format.setLenient(false);
        try {
            return format.parse(convert);
        } catch (ParseException e) {
            throw new RuntimeException("Invalid Date");
        }
    }
}
