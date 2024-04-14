package com.example.ourivesaria.controllers.admin;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/admin/auth")
public class AdminAuthController {


    // Authentication for admin
    @RequestMapping(method = RequestMethod.GET, path = {"/"})
    public ResponseEntity<String> authAdmin() {

        return new ResponseEntity<>("Authenticated with success!",HttpStatus.OK);
    }

}
