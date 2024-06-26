package org.choongang.admin.controllers;

import org.choongang.global.config.annotations.Controller;
import org.choongang.global.config.annotations.GetMapping;
import org.choongang.global.config.annotations.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController { // Admin 의 main

    @GetMapping
    public String index() {
        return "admin/index";
    }
}