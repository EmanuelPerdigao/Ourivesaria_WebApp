package com.example.ourivesaria.controllers.admin;


import com.example.ourivesaria.dtos.products.ProductDto;
import com.example.ourivesaria.entities.products.ProductEntity;
import com.example.ourivesaria.mappers.products.ProductMapper;
import com.example.ourivesaria.services.products.ProductService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "https://ourivesaria-webapp.onrender.com", maxAge = 3600)
@RequestMapping("/admin/product")
public class AdminProductController {

    private ProductService productService;
    private final ProductMapper productMapper;


    public AdminProductController(ProductService productService, ProductMapper productMapper) {
        this.productService = productService;
        this.productMapper = productMapper;
    }

    //#############################  GET ALL PRODUCTS  ####################################
    @RequestMapping(method = RequestMethod.GET, path = {"/list"})
    public ResponseEntity<List<ProductDto>> listProducts() {

        List<ProductEntity> products = productService.listAllProducts();

        List<ProductDto> productDtoList = productMapper.convertToDTOList(products);


        return new ResponseEntity<>(productDtoList, HttpStatus.OK);
    }




    //#############################  GET A Product BY ID  ####################################

    @RequestMapping(method = RequestMethod.GET, path = {"/{id}"})
    public ResponseEntity<ProductDto> findProductsById(@Valid @PathVariable Long id){
        ProductDto productDto = null;

        Optional<ProductEntity> product = productService.getById(id);

        if (!product.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }else {
            productDto = productMapper.convertToDto(product.get());
        }

        return new ResponseEntity<>(productDto, HttpStatus.OK);
    }



    //#############################  SAVE A Product  ########################################
    @RequestMapping(method = RequestMethod.POST, path = {"/save"})
    public ResponseEntity<String> saveProduct(@Valid @RequestBody ProductDto product) {

        productService.saveProduct(productMapper.convertToEntity(product));

        return new ResponseEntity<>("Product saved successfully", HttpStatus.OK);
    }


    //#############################  UPDATE A Product  ########################################
    @RequestMapping(method = RequestMethod.PUT, path = {"/update"})
    public ResponseEntity<String> updateProduct(@Valid @RequestBody ProductDto product) {

        if (!productService.getById(product.id()).isPresent()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        productService.updateProduct(productMapper.convertToEntity(product));

        return new ResponseEntity<>("Product updated successfully", HttpStatus.OK);
    }




    //#############################  DELETE A PRODUCT  ########################################
    @RequestMapping(method = RequestMethod.DELETE, path = {"/delete/{id}"})
    public ResponseEntity<String> deleteProduct(@Valid @PathVariable long id) {

        if (!productService.getById(id).isPresent()) {
            return new ResponseEntity<>("Product not found", HttpStatus.NOT_FOUND);
        }
        productService.deleteProduct(id);

        return new ResponseEntity<>("Product deleted successfully", HttpStatus.OK);
    }


}
