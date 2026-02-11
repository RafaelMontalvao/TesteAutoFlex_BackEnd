package com.Autoflex.TestePratico.service;


import com.Autoflex.TestePratico.dto.ProductMaterialRequestDto;
import com.Autoflex.TestePratico.dto.ProductMaterialResponseDto;
import com.Autoflex.TestePratico.exception.AssociationNotFoundException;
import com.Autoflex.TestePratico.exception.MaterialNotFoundException;
import com.Autoflex.TestePratico.exception.ProductNotFoundException;
import com.Autoflex.TestePratico.model.Product;
import com.Autoflex.TestePratico.model.ProductMaterial;
import com.Autoflex.TestePratico.model.RawMaterial;
import com.Autoflex.TestePratico.repository.ProductMaterialRepository;
import com.Autoflex.TestePratico.repository.ProductRepository;
import com.Autoflex.TestePratico.repository.RawMaterialRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductMaterialService{

    private final ProductMaterialRepository repo;
    private final RawMaterialRepository materialRepo;
    private final ProductRepository productRepo;


    public ProductMaterial associateMaterial(Integer productId, ProductMaterialRequestDto productMaterialRequestDto){

        Optional<Product> product = productRepo.findById(productId);
        if(product.isEmpty())
            throw new ProductNotFoundException();
        Optional<RawMaterial> material = materialRepo.findById(productMaterialRequestDto.getRawMaterialId());
        if(material.isEmpty())
            throw new MaterialNotFoundException();

        if(repo.existsByProductIdAndRawMaterialId(productId,productMaterialRequestDto.getRawMaterialId()))
            throw new RuntimeException("Material já associado a este produto");



        Product productBd = product.get();
        RawMaterial rawMaterialBd = material.get();

        ProductMaterial productMaterial = new ProductMaterial();
        productMaterial.setProduct(productBd);
        productMaterial.setRawMaterial(rawMaterialBd);
        productMaterial.setRequeiredQuantity(productMaterialRequestDto.getQuantityNeeded());

        return  repo.save(productMaterial);

    }


    public List<ProductMaterial> getAllAssociations(){
        List<ProductMaterial> list = repo.findAll();
        System.out.println("TOTAL BANCO: " + list.size());
        return list;
    }

    public ProductMaterial updateMaterial(Integer id, ProductMaterialRequestDto productMaterialRequestDto){
        Optional<ProductMaterial> material = repo.findById(id);

        if(material.isEmpty())
            throw new AssociationNotFoundException();
        ProductMaterial productMaterialBd = material.get();

        if(productMaterialRequestDto.getQuantityNeeded()!= null){
            productMaterialBd.setRequeiredQuantity(productMaterialRequestDto.getQuantityNeeded());
        }
        if(productMaterialRequestDto.getRawMaterialId() != null){
            productMaterialBd.setId(productMaterialRequestDto.getRawMaterialId());
        }
        return  repo.save(productMaterialBd);
    }

    public void delete(Integer id){
        boolean existAssociation = repo.existsById(id);
        if(!existAssociation)
            throw new AssociationNotFoundException();
        repo.deleteById(id);
    }


}
