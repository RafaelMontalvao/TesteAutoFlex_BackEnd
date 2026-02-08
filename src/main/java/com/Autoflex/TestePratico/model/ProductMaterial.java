package com.Autoflex.TestePratico.model;


import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Entity
@Table(name="product_material")
public class ProductMaterial {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name="product_id")
    @JsonBackReference
    private Product product;

    @ManyToOne
    @JoinColumn(name="rawn_material_id")
    private RawMaterial rawMaterial;

    private BigDecimal requeiredQuantity;


}
