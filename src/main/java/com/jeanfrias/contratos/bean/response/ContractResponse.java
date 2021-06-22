package com.jeanfrias.contratos.bean.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ContractResponse {

    private String contractId;

    private CustomerResponse customer;

    private JobResponse job;

    private String endOfContract;

}
