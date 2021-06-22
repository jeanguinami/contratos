package com.jeanfrias.contratos.bean.request;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ContractRequest {

    private String contractId;

    private String customerId;

    private String jobId;

    private String endOfContract;

}
