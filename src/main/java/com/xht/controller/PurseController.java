package com.xht.controller;

import com.xht.pojo.Purse;
import com.xht.service.PurseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PurseController {
    @Autowired
    PurseService purseService;

    @GetMapping("/getPurse")
    public Purse getPurseById(int uid){
        return purseService.getPurseById(uid);
    }
}
