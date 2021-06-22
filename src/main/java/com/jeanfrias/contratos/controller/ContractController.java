package com.jeanfrias.contratos.controller;

import com.jeanfrias.contratos.bean.request.ContractRequest;
import com.jeanfrias.contratos.bean.response.ContractResponse;
import com.jeanfrias.contratos.service.ContractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;

@RestController
@RequestMapping("/contracts")
public class ContractController {

    @Autowired
    private ContractService contractService;

    @GetMapping("/customer/{n}")
    public ArrayList<ContractResponse> getContractByCnpj(
            @PathVariable("n") String customerCnpj,
            @RequestParam(value = "offset", defaultValue = "0") Integer offset,
            @RequestParam(value = "limit", defaultValue = "10") Integer limit){
        return contractService.getContractsByCustomerCnpj(customerCnpj, offset, limit);
    }

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public void createContract(@RequestBody @Valid ContractRequest request){
        contractService.createContract(request);
    }

    @PutMapping
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void updateContract(@RequestBody @Valid ContractRequest request){
        contractService.updateContract(request);
    }

    @DeleteMapping("/remove/{c}")
    public void removeContract (@PathVariable("c") String contractId){
        contractService.deleteContract(contractId);
    }
}
