package com.example.ourivesaria.configs.profileconfigs;

import com.example.ourivesaria.repositories.users.UserRepository;
import com.example.ourivesaria.services.category.CategoryService;
import com.example.ourivesaria.services.material.MaterialService;
import com.example.ourivesaria.services.products.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
@Profile("dev")
public class DevConfig implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private final MaterialService materialService;
    private final CategoryService categoryService;
    private final ProductService productService;

    @Override
    public void run(String... args) throws Exception {

    }


}
