package com.Autoflex.TestePratico.controller;

import com.Autoflex.TestePratico.dto.ProductMaterialRequestDto;
import com.Autoflex.TestePratico.dto.ProductMaterialResponseDto;
import com.Autoflex.TestePratico.dto.ProductRequestDto;
import com.Autoflex.TestePratico.model.ProductMaterial;
import com.Autoflex.TestePratico.service.ProductMaterialService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;


@RestController
@RequestMapping("product_material")
@CrossOrigin(origins="http://localhost:5173")
public class ProductMaterialController {

    private final ModelMapper mapper;
    private final ProductMaterialService service;


    public ProductMaterialController(ModelMapper mapper, ProductMaterialService service) {
        this.mapper = mapper;
        this.service = service;
    }

    @PostMapping("/{productId}/material")
    public ResponseEntity<ProductMaterialResponseDto> associateMaterial(
            @PathVariable Integer productId,
            @RequestBody @Valid ProductMaterialRequestDto request) {
        ProductMaterial association = mapper.map(request, ProductMaterial.class);

        association = service.associateMaterial(productId, request);

        ProductMaterialResponseDto responseDto = new ProductMaterialResponseDto();

        responseDto.setRawMaterialId(association.getRawMaterial().getId());
        responseDto.setQuantityNeeded(association.getRequeiredQuantity());
        responseDto.setRawMaterialName(association.getRawMaterial().getName());
            URI uri = UriComponentsBuilder
                .fromPath("/api/Product_Material/{id}/material")
                .buildAndExpand(association.getId())
                .toUri();

        return ResponseEntity.ok(responseDto);
    }


    @PutMapping("/edit/{id}")
    public  ResponseEntity<ProductMaterialResponseDto> updateMaterial(@PathVariable Integer id, @RequestBody @Valid ProductMaterialRequestDto productRequestDto) {
        ProductMaterial material = mapper.map(productRequestDto, ProductMaterial.class);
        material = service.updateMaterial(id,productRequestDto);
        ProductMaterialResponseDto responseDto = mapper.map(material, ProductMaterialResponseDto.class);
        return ResponseEntity.ok(responseDto);

    }


    @GetMapping("/get")
    public List<ProductMaterial> getAllAssociations(){
        return  service.getAllAssociations();
     }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity delete(@PathVariable("id") Integer id){
        service.delete(id);
        return ResponseEntity.noContent().build();
    }




}
