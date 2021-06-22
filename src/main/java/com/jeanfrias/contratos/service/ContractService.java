package com.jeanfrias.contratos.service;

import com.jeanfrias.contratos.bean.ejb.Contract;
import com.jeanfrias.contratos.bean.ejb.Customer;
import com.jeanfrias.contratos.bean.ejb.Job;
import com.jeanfrias.contratos.bean.request.ContractRequest;
import com.jeanfrias.contratos.bean.response.ContractResponse;
import com.jeanfrias.contratos.bean.response.CustomerResponse;
import com.jeanfrias.contratos.bean.response.JobResponse;
import com.jeanfrias.contratos.repository.ContractRepository;
import com.jeanfrias.contratos.repository.CustomerRepository;
import com.jeanfrias.contratos.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static org.apache.commons.lang3.StringUtils.isBlank;

@Service
public class ContractService {
    
    @Autowired
    ContractRepository contractRepository;
    
    @Autowired
    CustomerRepository customerRepository;
    
    @Autowired
    JobRepository jobRepository;
    
    public void createContract(ContractRequest contractReq) throws RuntimeException {
        Contract contract = buildContract(contractReq);
        contractRepository.save(contract);
    }

    public void updateContract(ContractRequest request) {
        Contract contract = contractRepository.findById(Long.valueOf(request.getContractId()))
                .orElseThrow(() -> new RuntimeException("Invalid Contract"));

        contract.setCustomer(customerRepository.findById(Long.valueOf(request.getCustomerId()))
                .orElseThrow(() -> new RuntimeException("Invalid Customer")));

        contract.setJob(jobRepository.findById(Long.valueOf(request.getJobId()))
                .orElseThrow(() -> new RuntimeException("Invalid Job")));

        contract.setEndOfContract(convertStringToDate(request.getEndOfContract()));

        contractRepository.save(contract);
    }

    public void deleteContract(String contractId) throws RuntimeException {
        Contract contract = contractRepository.findById(Long.valueOf(contractId))
                .orElseThrow(() -> new RuntimeException("Contract isn't on database"));
        contractRepository.delete(contract);
    }

    public ArrayList<ContractResponse> getContractsByCustomerCnpj(String cnpj, Integer offset, Integer limit) {
        Pageable pagination = PageRequest.of(offset, limit, Sort.unsorted());
        ArrayList<ContractResponse> contractResponseList = new ArrayList<>();

        Page<Contract> contracts = contractRepository.findByCustomerCnpj(cnpj, pagination);
        contracts.forEach((c) -> contractResponseList.add(buildContractResponse(c)));

        return contractResponseList;
    }

    private Contract buildContract(ContractRequest contractReq) {
        if(!isBlank(contractReq.getCustomerId()) && !isBlank(contractReq.getJobId())
                && !isBlank(contractReq.getEndOfContract())){

            Customer customer = customerRepository.findById(Long.valueOf(contractReq.getCustomerId()))
                    .orElseThrow(() -> new RuntimeException("Invalid Customer"));

            Job job = jobRepository.findById(Long.valueOf(contractReq.getJobId()))
                    .orElseThrow(() -> new RuntimeException("Invalid Job"));

            return Contract.builder()
                    .customer(customer)
                    .job(job)
                    .endOfContract(convertStringToDate(contractReq.getEndOfContract()))
                    .build();
        } else {
            throw new RuntimeException("Invalid Contract");
        }
    }

    private ContractResponse buildContractResponse(Contract contract){
        ContractResponse contractResp = new ContractResponse();

        contractResp.setContractId(String.valueOf(contract.getId()));
        contractResp.setCustomer(buildCustomerResponse(contract.getCustomer()));
        contractResp.setJob(buildJobResponse(contract.getJob()));
        contractResp.setEndOfContract(contract.getEndOfContract().toString());

        return contractResp;
    }

    private CustomerResponse buildCustomerResponse(Customer customer) {

        CustomerResponse customerResp = new CustomerResponse();

        customerResp.setCustomerId(String.valueOf(customer.getId()));
        customerResp.setCustomerCnpj(customer.getCnpj());
        customerResp.setCustomerName(customer.getName());

        return customerResp;
    }

    private JobResponse buildJobResponse(Job job) {

        JobResponse jobResp = new JobResponse();

        jobResp.setJobId(String.valueOf(job.getId()));
        jobResp.setJobName(job.getName());

        return jobResp;
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
