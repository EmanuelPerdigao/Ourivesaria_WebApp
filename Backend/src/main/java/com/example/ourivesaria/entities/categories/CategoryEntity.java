package com.example.ourivesaria.entities.categories;

import com.example.ourivesaria.entities.products.ProductEntity;
import com.example.ourivesaria.enums.CategoryEnum;
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
@Entity
@Table(name = "category")
public class CategoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true , name="category_name")
    @Enumerated(EnumType.STRING)
    private CategoryEnum categoryName;


    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "categoryEntity", cascade = CascadeType.ALL)
    private List<ProductEntity> productList;
}
