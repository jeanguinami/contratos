package com.jeanfrias.contratos.bean.ejb;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "JOB")
@NamedQuery(name="Job.findAll", query="SELECT i FROM Job i")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Job implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "JOB_ID")
    private Long id;

    @Column(name = "NAME")
    private String name;

}
