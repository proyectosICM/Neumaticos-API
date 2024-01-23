package com.icm.TireManagementApi.Controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Prueba {
    @GetMapping("/hello")
    public String hello(){
        return "Hello World Not Secured";
    }
}
