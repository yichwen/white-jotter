package org.ursprung.wj.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.ursprung.wj.result.Result;
import org.ursprung.wj.result.ResultFactory;
import org.ursprung.wj.service.AdminMenuService;

@RestController
public class MenuController {

    @Autowired
    private AdminMenuService adminMenuService;

    @GetMapping("/api/menu")
    public Result menu() {
        return ResultFactory.buildSuccessResult(adminMenuService.getMenusByCurrentUser());
    }

    @GetMapping("/api/admin/role/menu")
    public Result listAllMenu() {
        return ResultFactory.buildSuccessResult(adminMenuService.getAdminMenus());
    }

}
