package com.jeanfrias.contratos.bean.request;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JobRequest {

    private String jobId;

    private String jobName;

}
