package com.jeanfrias.contratos.bean.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerResponse {

    private String customerId;

    private String customerName;

    private String customerCnpj;
}
