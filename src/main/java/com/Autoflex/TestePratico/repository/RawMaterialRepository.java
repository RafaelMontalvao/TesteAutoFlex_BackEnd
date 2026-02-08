package com.Autoflex.TestePratico.repository;

import com.Autoflex.TestePratico.model.Product;
import com.Autoflex.TestePratico.model.RawMaterial;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RawMaterialRepository extends JpaRepository<RawMaterial, Integer> {

    boolean existsByName(String name);

}
