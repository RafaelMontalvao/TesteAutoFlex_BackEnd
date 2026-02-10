package com.Autoflex.TestePratico.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cron")
public class CronController {


    @GetMapping
    public String ping() {
        return "ok";
    }


}
