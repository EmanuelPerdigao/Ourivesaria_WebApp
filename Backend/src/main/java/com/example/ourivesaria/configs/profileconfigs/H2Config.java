package com.example.ourivesaria.configs.profileconfigs;

import com.example.ourivesaria.entities.categories.CategoryEntity;
import com.example.ourivesaria.entities.materials.MaterialEntity;
import com.example.ourivesaria.entities.products.ProductEntity;
import com.example.ourivesaria.entities.users.UserEntity;
import com.example.ourivesaria.repositories.users.UserRepository;
import com.example.ourivesaria.enums.CategoryEnum;
import com.example.ourivesaria.enums.MaterialEnum;
import com.example.ourivesaria.enums.UserRoles;
import com.example.ourivesaria.services.category.CategoryService;
import com.example.ourivesaria.services.material.MaterialService;
import com.example.ourivesaria.services.products.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Component
@Profile("h2")
public class H2Config implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private final MaterialService materialService;
    private final CategoryService categoryService;
    private final ProductService productService;

    @Override
    public void run(String... args) throws Exception {

        System.out.println("H2 database environment/profile");

        UserEntity admin = new UserEntity();
        admin.setUserName("Ourivesaria");
        admin.setPassword(passwordEncoder.encode("admin"));
        admin.setRoles(UserRoles.ROLE_ADMIN);
        admin.setEmailId("admin@gmail.com");

        UserEntity user = new UserEntity();
        user.setUserName("user");
        user.setPassword(passwordEncoder.encode("1234"));
        user.setRoles(UserRoles.ROLE_USER);
        user.setEmailId("user@gmail.com");

        List<UserEntity> userList = new ArrayList<>();

        userList.add(admin);
        userList.add(user);

        userRepository.saveAll(userList);


        for (MaterialEnum materialEnum: MaterialEnum.values()) {

            MaterialEntity materialEntity = new MaterialEntity();

            switch (materialEnum) {
                case OURO:
                    materialEntity.setMaterialName(MaterialEnum.OURO);
                    materialService.saveMaterial(materialEntity);
                    break;
                case PRATA:
                    materialEntity.setMaterialName(MaterialEnum.PRATA);
                    materialService.saveMaterial(materialEntity);
                    break;
                case OUTROS:
                    materialEntity.setMaterialName(MaterialEnum.OUTROS);
                    materialService.saveMaterial(materialEntity);
                    break;
            }
        }

        //Creat inital categories
        for (CategoryEnum categoryEnum: CategoryEnum.values()){

            switch (categoryEnum) {

                case ANEIS, COLARES, PULSEIRAS, BRINCOS, RELOGIOS:
                    CategoryEntity categoryEntityGold = new CategoryEntity();
                    categoryEntityGold.setCategoryName(categoryEnum);
                    /*categoryEntityGold.setMaterialEntity(materialService.getById(1L).get());*/
                    categoryService.saveCategory(categoryEntityGold);
                    break;
            }
        }


        ProductEntity productEntityAnelOuro = new ProductEntity();
        productEntityAnelOuro.setProductName("Anel de ouro");
        productEntityAnelOuro.setProductDescription("anel de ouro de 18kt");
        productEntityAnelOuro.setProductImg("https://images.unsplash.com/photo-1598560917807-1bae44bd2be8?q=80&w=1780&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D");


        productEntityAnelOuro.setCategoryEntity(categoryService.getById(1L).get());
        productEntityAnelOuro.setMaterialEntity(materialService.getById(2L).get());

        productService.saveProduct(productEntityAnelOuro);


        ProductEntity productEntityAnelPrata = new ProductEntity();
        productEntityAnelPrata.setProductName("Anel de prata");
        productEntityAnelPrata.setProductDescription("anel de prata de 18kt");
        productEntityAnelPrata.setProductImg("https://images.unsplash.com/photo-1586104195538-050b9f74f58e?q=80&w=2070&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D");


        productEntityAnelPrata.setCategoryEntity(categoryService.getById(1L).get());
        productEntityAnelPrata.setMaterialEntity(materialService.getById(1L).get());

        productService.saveProduct(productEntityAnelPrata);


        ProductEntity productEntityColarPrata = new ProductEntity();
        productEntityColarPrata.setProductName("Colar de prata");
        productEntityColarPrata.setProductDescription("Colar de prata de 18kt");
        productEntityColarPrata.setProductImg("https://images.unsplash.com/photo-1619119069152-a2b331eb392a?q=80&w=2071&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D");

        productEntityColarPrata.setCategoryEntity(categoryService.getById(3L).get());
        productEntityColarPrata.setMaterialEntity(materialService.getById(1L).get());

        productService.saveProduct(productEntityColarPrata);




    }
}
