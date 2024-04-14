package com.example.ourivesaria.entities.materials;

import com.example.ourivesaria.entities.products.ProductEntity;
import com.example.ourivesaria.enums.MaterialEnum;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name="material")
@Entity
public class MaterialEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(nullable = false,unique = true, name="material_name")
    @Enumerated(EnumType.STRING)
    private MaterialEnum materialName;

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "materialEntity", cascade = CascadeType.ALL)
    private List<ProductEntity> productList;
}
