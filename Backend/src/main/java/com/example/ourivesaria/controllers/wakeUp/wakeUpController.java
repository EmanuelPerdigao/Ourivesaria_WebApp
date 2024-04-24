package com.example.ourivesaria.controllers.wakeUp;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/server")
public class wakeUpController {

    //#############################  WAKE UP THE SERVER  ####################################
    @RequestMapping(method = RequestMethod.GET, path = {"/wakeup"})
    public ResponseEntity<String> wakeUpServer() {

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
