package com.Autoflex.TestePratico.controller;

import com.Autoflex.TestePratico.dto.RawMaterialRequestDto;
import com.Autoflex.TestePratico.dto.RawMaterialResponseDto;
import com.Autoflex.TestePratico.dto.RawMaterialUpdateRequestDto;
import com.Autoflex.TestePratico.model.RawMaterial;
import com.Autoflex.TestePratico.service.RawMaterialService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/raw_material")
@CrossOrigin(origins="http://localhost:5173")
public class RawMaterialController {

    private final ModelMapper mapper;
    private final RawMaterialService service;

    public RawMaterialController(ModelMapper mapper, RawMaterialService service ){
        this.mapper = mapper;
        this.service = service;
    }

    @PostMapping("/create")
    public ResponseEntity<RawMaterialResponseDto> createMaterial(@RequestBody @Valid RawMaterialRequestDto request) {
        RawMaterial  material = mapper.map(request, RawMaterial.class);
        material = service.createRawMaterial(material);
        RawMaterialResponseDto responseDto = mapper.map(material, RawMaterialResponseDto.class);
        URI uri = UriComponentsBuilder
                .fromPath("/api/raw_material/{id}")
                .buildAndExpand(material.getId())
                .toUri();
        return ResponseEntity.created(uri).body(responseDto);

    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<RawMaterialResponseDto> updateRawMaterial(@PathVariable Integer id, @RequestBody @Valid RawMaterialUpdateRequestDto request) {
        RawMaterial  material = mapper.map(request, RawMaterial.class);
        material = service.updateRawMaterial(id, request);
        RawMaterialResponseDto responseDto = mapper.map(material, RawMaterialResponseDto.class);
        return ResponseEntity.ok(responseDto);
    }


    @GetMapping("/get")
    public ResponseEntity<List<RawMaterial>> getAllMaterials() {
        return ResponseEntity.ok(service.getAllMaterials());
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<RawMaterialResponseDto> getMaterialById(@PathVariable Integer id) {
        RawMaterial material = service.getMaterialById(id);
        RawMaterialResponseDto responseDto = mapper.map(material, RawMaterialResponseDto.class);
        return ResponseEntity.ok(responseDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable("id") Integer id){
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

}
