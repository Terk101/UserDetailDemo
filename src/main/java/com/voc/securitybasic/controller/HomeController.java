package com.voc.securitybasic.controller;

import com.voc.securitybasic.domain.User;
import com.voc.securitybasic.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @Autowired
    private IUserService iUserService;

    @GetMapping("/home")
    public String getHomePage() {
        return "homePage";
    }

    @GetMapping("/welcome")
    public String getWelcomePage() {
        return "welcomePage";
    }

    @GetMapping("/admin")
    public String getAdminPage() {
        return "adminPage";
    }

    @GetMapping("/emp")
    public String getEmployeePage() {
        return "empPage";
    }

    @GetMapping("/mgr")
    public String getManagerPage() {
        return "mgrPage";
    }

    @GetMapping("/common")
    public String getCommonPage() {
        return "commonPage";
    }

    @GetMapping("/accessDenied")
    public String getAccessDeniedPage() {
        return "accessDeniedPage";
    }

    @PostMapping("/saveUser")
    public Long saveUser(@RequestBody User user) {
        return iUserService.saveUser(user);
    }
}
