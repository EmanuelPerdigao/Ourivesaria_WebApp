package com.example.ourivesaria.controllers.products;

import com.example.ourivesaria.entities.products.ProductEntity;
import com.example.ourivesaria.services.products.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "https://ourivesaria.onrender.com", maxAge = 3600)
@Slf4j
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductService productService;


    //#############################  LIST ALL CREATED PRODUCTS  ####################################
    @RequestMapping(method = RequestMethod.GET, path = {"/list"})
    public ResponseEntity<List<ProductEntity>> listAllProducts(){

        List<ProductEntity> productList = productService.listAllProducts();

        return new ResponseEntity<>(productList, HttpStatus.OK);
    }


    //#############################  GET ALL PRODUCTS BY CATEGORY ID  ####################################
    @RequestMapping(method = RequestMethod.GET, path = {"/listByCategory/{categoryId}"})
    public ResponseEntity<List<ProductEntity>> listProductsByCategory(@PathVariable Long categoryId) {

        List<ProductEntity> productListByCategory = productService.listAllProductsByCategoryId(categoryId);

        return new ResponseEntity<>(productListByCategory, HttpStatus.OK);
    }


    //#############################  GET ALL PRODUCTS BY CATEGORY Name and MaterialEntity  ####################################
    @RequestMapping(method = RequestMethod.GET, path = {"/listByCategory/{categoryId}/material/{materialId}"})
    public ResponseEntity<List<ProductEntity>> listProductsByCategoryAndMaterial(@PathVariable Long categoryId, @PathVariable Long materialId) {

        List<ProductEntity> productListByCategory = productService.listAllProductsByCategoryIdAndMaterialId( categoryId, materialId);

        return new ResponseEntity<>(productListByCategory, HttpStatus.OK);
    }
}
