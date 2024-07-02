package com.enoch.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.enoch.model.Currency;

public interface CurrencyRepository extends JpaRepository<Currency, Integer>  {}