package com.jeanfrias.contratos.repository;

import com.jeanfrias.contratos.bean.ejb.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long>, PagingAndSortingRepository<Customer, Long> {

    @Query("SELECT C FROM Customer C WHERE C.name LIKE concat('%', ?1, '%')")
    Page<Customer> findByCustomerName(String customerName, Pageable pagination);

    @Query("SELECT count(C) FROM Customer C WHERE (C.cnpj = ?1)")
    Integer countCnpj(String cnpj);
}