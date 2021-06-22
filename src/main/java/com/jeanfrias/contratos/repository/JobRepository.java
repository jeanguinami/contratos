package com.jeanfrias.contratos.repository;

import com.jeanfrias.contratos.bean.ejb.Job;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JobRepository extends JpaRepository<Job, Long>, PagingAndSortingRepository<Job, Long> {

    @Query("SELECT J FROM Job J WHERE J.name LIKE concat('%', ?1, '%')")
    Page<Job> findByName(String jobName, Pageable pagination);

    @Query("SELECT count(J) FROM Job J WHERE (J.name = ?1)")
    Integer countName(String jobName);
}
