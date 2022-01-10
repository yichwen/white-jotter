package org.ursprung.wj.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.ursprung.wj.dto.AdminUserPasswordDto;
import org.ursprung.wj.dto.AdminUserStatusDto;
import org.ursprung.wj.dto.AdminUserUpdateDto;
import org.ursprung.wj.result.Result;
import org.ursprung.wj.result.ResultFactory;
import org.ursprung.wj.service.UserService;

@RestController
@RequestMapping("/api/admin/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public Result listUsers() {
        return ResultFactory.buildSuccessResult(userService.getAdminUsers());
    }

    @PutMapping("/status")
    public Result updateUserStatus(@RequestBody AdminUserStatusDto adminUserStatusDto) {
        userService.updateStatus(adminUserStatusDto);
        return ResultFactory.buildSuccessResult("用户状态更新成功");
    }

    @PutMapping("/password")
    public Result resetPassword(@RequestBody AdminUserPasswordDto adminUserPasswordDto) {
        userService.resetPassword(adminUserPasswordDto);
        return ResultFactory.buildSuccessResult("重置密码成功");
    }

    @PutMapping
    public Result editUser(@RequestBody AdminUserUpdateDto adminUserUpdateDto) {
        userService.updateUser(adminUserUpdateDto);
        return ResultFactory.buildSuccessResult("修改用户信息成功");
    }
}
