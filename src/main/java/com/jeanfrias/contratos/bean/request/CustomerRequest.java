package com.jeanfrias.contratos.bean.request;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerRequest {

    private String customerId;

    private String customerName;

    private String customerCnpj;
}
