package com.jeanfrias.contratos.repository;

import com.jeanfrias.contratos.bean.ejb.Contract;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContractRepository extends JpaRepository<Contract, Long>, PagingAndSortingRepository<Contract, Long> {

    @Query("SELECT C FROM Contract C WHERE C.customer.cnpj = ?1")
    Page<Contract> findByCustomerCnpj(String cnpj, Pageable pagination);
}
