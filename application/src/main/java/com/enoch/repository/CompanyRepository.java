package com.enoch.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.enoch.model.Company;

public interface CompanyRepository extends JpaRepository<Company, Long> {}