package com.jeanfrias.contratos.bean.ejb;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "CONTRACT")
@NamedQuery(name="Contract.findAll", query="SELECT c FROM Contract c")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Contract implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "CONTRACT_ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "CUSTOMER_ID", referencedColumnName = "CUSTOMER_ID")
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "JOB_ID", referencedColumnName = "JOB_ID")
    private Job job;

    @Column(name = "END_OF_CONTRACT")
    private Date endOfContract;
}
