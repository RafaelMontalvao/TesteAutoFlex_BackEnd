package com.Autoflex.TestePratico.service;


import com.Autoflex.TestePratico.dto.RawMaterialUpdateRequestDto;
import com.Autoflex.TestePratico.exception.MaterialAlredyExistsException;
import com.Autoflex.TestePratico.exception.MaterialNotFoundException;
import com.Autoflex.TestePratico.exception.ProductNotFoundException;
import com.Autoflex.TestePratico.model.RawMaterial;
import com.Autoflex.TestePratico.repository.RawMaterialRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;

@Service
@RequiredArgsConstructor
public class RawMaterialService {

    private final RawMaterialRepository repo;

    public RawMaterial createRawMaterial(RawMaterial rawMaterial) {

        if (repo.existsByName(rawMaterial.getName())) {
            throw new MaterialAlredyExistsException();
        }

        return repo.save(rawMaterial);
    }

    public RawMaterial updateRawMaterial(Integer id, RawMaterialUpdateRequestDto request) {
        Optional<RawMaterial> rawMaterialOptional = repo.findById(id);
        if(!rawMaterialOptional.isPresent()) {
            throw new MaterialNotFoundException();
        }
        RawMaterial rawMaterial = rawMaterialOptional.get();
        if(request.getName() != null) {
            rawMaterial.setName(request.getName());
        }
        if(request.getStockQuantity() != null) {
            rawMaterial.setStockQuantity(request.getStockQuantity());
        }
        return repo.save(rawMaterial);



    }


    public List<RawMaterial> getAllMaterials() {
        return repo.findAll();
    }

    public RawMaterial getMaterialById(Integer id) {
        Optional<RawMaterial> rawMaterialOptional = repo.findById(id);

        if(rawMaterialOptional.isEmpty())
            throw new MaterialNotFoundException();

        RawMaterial rawMaterial = rawMaterialOptional.get();
        return rawMaterial;

    }

    public void delete (Integer id){
        boolean existsMaterial = repo.existsById(id);
        if(!existsMaterial)
            throw new ProductNotFoundException();
        repo.deleteById(id);


    }



}
