package com.Autoflex.TestePratico.service;

import com.Autoflex.TestePratico.dto.ProductAvailabilityDto;
import com.Autoflex.TestePratico.dto.ProductRequestDto;
import com.Autoflex.TestePratico.dto.ProductUpdateRequestDto;
import com.Autoflex.TestePratico.exception.ProductAlredyExistException;
import com.Autoflex.TestePratico.exception.ProductNotFoundException;
import com.Autoflex.TestePratico.exception.RegistroComNomeExistenteException;
import com.Autoflex.TestePratico.model.Product;
import com.Autoflex.TestePratico.model.ProductMaterial;
import com.Autoflex.TestePratico.repository.ProductMaterialRepository;
import com.Autoflex.TestePratico.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductMaterialRepository productMaterialRepository;

    public Product create(Product product) {
        if(productRepository.existsByCode(product.getCode()) || productRepository.existsByName(product.getName()))
            throw new ProductAlredyExistException();
        return productRepository.save(product);
    }

    public Product updateProduct( Integer  id , ProductUpdateRequestDto productRequestDto) {

        Optional<Product> product = productRepository.findById(id);
            if(product.isEmpty())
                throw new ProductNotFoundException();
                Product productBd =  product.get();
                if(productRequestDto.getCode() != null && !productRequestDto.getCode().isBlank()) {
                    productBd.setCode(productRequestDto.getCode());
                }
                if(productRequestDto.getName() != null && !productRequestDto.getName().isBlank()) {
                    productBd.setName(productRequestDto.getName());
                }
                if (productRequestDto.getPrice() != null ){
                    productBd.setPrice(productRequestDto.getPrice());
            }
        return productRepository.save(productBd);

    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product getProductById(Integer id) {
        Optional<Product> productOptional = productRepository.findById(id);
        if(productOptional.isEmpty())
            throw new ProductNotFoundException();
        Product product = productOptional.get();
        return product;

    }

    public List<ProductAvailabilityDto> getProductsProducible() {
        List<Product> products = productRepository.findAll();
        List<ProductAvailabilityDto> result = new ArrayList<>();



        for (Product product : products) {
            if(product.getMaterial().isEmpty()){
                continue;
            }

            BigDecimal minQuantity = null;
            boolean allMaterialsPossible = true;

            for (ProductMaterial material : product.getMaterial()){
                BigDecimal stock = material.getRawMaterial().getStockQuantity();
                BigDecimal needed = material.getRequeiredQuantity();


                if(needed.compareTo(BigDecimal.ZERO) <= 0){
                    continue;
                }

                BigDecimal possible = stock.divide(needed, 0, RoundingMode.FLOOR);

                if (possible.compareTo(BigDecimal.ONE) < 0) {
                    allMaterialsPossible = false;
                    break;
                }
                if (minQuantity == null || possible.compareTo(minQuantity) < 0) {
                    minQuantity = possible;
                }
            }

            if(allMaterialsPossible && minQuantity != null){

                BigDecimal totalValue = minQuantity.multiply(product.getPrice());

                ProductAvailabilityDto productAvailabilityDto = new ProductAvailabilityDto();
                productAvailabilityDto.setProductId(product.getId());
                productAvailabilityDto.setProductName(product.getName());
                productAvailabilityDto.setProductcode(product.getCode());
                productAvailabilityDto.setProducibleQuantity(minQuantity);
                productAvailabilityDto.setTotalProductionValue(totalValue);

                result.add(productAvailabilityDto);
            }


        }
        return result;


    }


    public void delete (Integer id){
        boolean existsProduct = productRepository.existsById(id);
        boolean existsAssociation =  productMaterialRepository.existsByProductId(id);
        if(!existsProduct)
            throw new ProductNotFoundException();
        if(existsAssociation)
            throw new RegistroComNomeExistenteException();
        productRepository.deleteById(id);


    }


}
