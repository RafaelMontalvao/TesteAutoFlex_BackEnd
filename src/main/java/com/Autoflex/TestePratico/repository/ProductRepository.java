package com.Autoflex.TestePratico.repository;

import com.Autoflex.TestePratico.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

    boolean existsByCode(String code);
    boolean existsByName (String name);
}

