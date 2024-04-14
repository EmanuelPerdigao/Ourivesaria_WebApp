package com.example.ourivesaria.entities.products;

import com.example.ourivesaria.entities.categories.CategoryEntity;
import com.example.ourivesaria.entities.materials.MaterialEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name="product")
@Entity
public class ProductEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;


    @Column(nullable = false, name="product_name")
    private String productName;


    @Column(nullable=false, name="product_description")
    private String productDescription;


    @Column(nullable = false, name="product_img")
    private String productImg;

    @ManyToOne
    @JoinColumn(nullable = false, name = "category_id")
    private CategoryEntity categoryEntity;

    @ManyToOne
    @JoinColumn(nullable = false, name="material_id")
    private MaterialEntity materialEntity;
}
