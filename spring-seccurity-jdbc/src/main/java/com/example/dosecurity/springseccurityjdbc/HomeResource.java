package com.example.dosecurity.springseccurityjdbc;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeResource {
    @GetMapping("/")
    public String home(){
        return "<h1> Welcome All</h1>";
    }
    @GetMapping("/user")
    public String user(){
        return "<h1> authenticated logged in Users</h1>";
    }
    @GetMapping("/admin")
    public String admin(){
        return "<h1>Just Admin Access</h1>";
    }
}
