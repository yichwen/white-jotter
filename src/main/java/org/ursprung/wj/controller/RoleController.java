package org.ursprung.wj.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.ursprung.wj.dto.AdminRoleCreateDto;
import org.ursprung.wj.dto.AdminRoleDto;
import org.ursprung.wj.dto.AdminRoleStatusDto;
import org.ursprung.wj.dto.AdminRoleUpdateDto;
import org.ursprung.wj.result.Result;
import org.ursprung.wj.result.ResultFactory;
import org.ursprung.wj.service.AdminPermissionService;
import org.ursprung.wj.service.AdminRoleService;

@RestController
@RequestMapping("/api/admin/role")
public class RoleController {

    @Autowired
    private AdminRoleService adminRoleService;

    @Autowired
    private AdminPermissionService adminPermissionService;

    @GetMapping
    public Result listRoles() {
        return ResultFactory.buildSuccessResult(adminRoleService.getAdminRoles());
    }

    @PutMapping("/status")
    public Result updateRoleStatus(@RequestBody AdminRoleStatusDto adminRoleStatusDto) {
        adminRoleService.updateRoleStatus(adminRoleStatusDto);
        return ResultFactory.buildSuccessResult(null);
    }

    @PutMapping
    public Result updateRole(@RequestBody AdminRoleUpdateDto adminRoleUpdateDto) {
        adminRoleService.updateRole(adminRoleUpdateDto);
        return ResultFactory.buildSuccessResult(null);
    }

    @PostMapping
    public Result addRole(@RequestBody AdminRoleCreateDto adminRoleCreateDto) {
        adminRoleService.addRole(adminRoleCreateDto);
        return ResultFactory.buildSuccessResult(null);
    }

    @GetMapping("/perm")
    public Result listPermissions() {
        return ResultFactory.buildSuccessResult(adminPermissionService.getAdminPermissions());
    }

//    @PutMapping("/menu")
//    public Result updateRoleMenu() {
//        return null;
//    }

}
