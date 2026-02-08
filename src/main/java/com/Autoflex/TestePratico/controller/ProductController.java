package com.Autoflex.TestePratico.controller;
import com.Autoflex.TestePratico.dto.*;
import com.Autoflex.TestePratico.model.Product;
import com.Autoflex.TestePratico.service.ProductService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;


@RestController
@RequestMapping("products")
@CrossOrigin(origins="http://localhost:3000")
public class ProductController {

    private final ModelMapper mapper;
    private final ProductService productService;

    public ProductController(ModelMapper mapper, ProductService productService) {
        this.mapper = mapper;
        this.productService = productService;
    }

    @PostMapping("/create")
    public ResponseEntity<ProductResponseDto> createProduct(@RequestBody   @Valid ProductRequestDto productRequestDto){
        Product  product = mapper.map(productRequestDto, Product.class);
        product = productService.create(product);
        ProductResponseDto productResponseDto = mapper.map(product, ProductResponseDto.class);
        URI uri = UriComponentsBuilder
                .fromPath("/api/products/{id}")
                .buildAndExpand(product.getId())
                .toUri();
        return ResponseEntity.created(uri).body(productResponseDto);
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<ProductResponseDto> updateProduct(@PathVariable("id") Integer id, @RequestBody @Valid ProductUpdateRequestDto productRequestDto){
            Product  product = mapper.map(productRequestDto, Product.class);
            product = productService.updateProduct(id,productRequestDto);
            ProductResponseDto productResponseDto = mapper.map(product, ProductResponseDto.class);
            return ResponseEntity.ok(productResponseDto);

    }

    @GetMapping("/get")
    public ResponseEntity<List<ProductResponseDto>> getAllProducts() {
        List<Product> products = productService.getAllProducts();


        List<ProductResponseDto> response = products.stream().map(product -> {
            ProductResponseDto dto = new ProductResponseDto();
            dto.setId(product.getId());
            dto.setName(product.getName());
            dto.setCode(product.getCode());
            dto.setPrice(product.getPrice());

            List<ProductMaterialResponseDto> materials = product.getMaterial().stream().map(pm -> {
                ProductMaterialResponseDto pmDto = new ProductMaterialResponseDto();
                pmDto.setRawMaterialId(pm.getRawMaterial().getId());
                pmDto.setRawMaterialName(pm.getRawMaterial().getName());
                pmDto.setQuantityNeeded(pm.getRequeiredQuantity());
                return pmDto;
            }).toList();

            dto.setMaterials(materials);

            return dto;
        }).toList();

        return ResponseEntity.ok(response);
    }


//
//    @GetMapping
//    public ResponseEntity<List<Product>> getAllProducts() {
//        return ResponseEntity.ok(productService.getAllProducts());
//    }

    @GetMapping("/get/{id}")
    public ResponseEntity<ProductResponseDto> getProductById(@PathVariable Integer id){
        Product product = productService.getProductById(id);
        ProductResponseDto productResponseDto = mapper.map(product, ProductResponseDto.class);
        return ResponseEntity.ok(productResponseDto);
    }


    @GetMapping("/available")
    public ResponseEntity<List<ProductAvailabilityDto>> getAvailableProducts () {
        List<ProductAvailabilityDto> avaiable = productService.getProductsProducible();
        return ResponseEntity.ok(avaiable);
    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity delete(@PathVariable("id") Integer id){
        productService.delete(id);
        return ResponseEntity.noContent().build();
    }


}
