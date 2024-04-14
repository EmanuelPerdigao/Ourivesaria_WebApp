package com.example.ourivesaria.configs.profileconfigs;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("prod")
public class ProdConfig implements CommandLineRunner {


    @Override
    public void run(String... args) throws Exception {

    }
}
