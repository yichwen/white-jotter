package org.ursprung.wj.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.ursprung.wj.dao.UserRepository;
import org.ursprung.wj.dto.*;
import org.ursprung.wj.entity.AdminRole;
import org.ursprung.wj.entity.User;

import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userDAO;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AdminRoleService adminRoleService;

    private static final Function<AdminRole, UserDto.RoleDto> roleMapper = role -> {
        UserDto.RoleDto roleDto = new UserDto.RoleDto();
        BeanUtils.copyProperties(role, roleDto);
        return roleDto;
    };

    private static final Function<AdminRole, AdminUserDto.RoleDto> adminRoleMapper = role -> {
        AdminUserDto.RoleDto roleDto = new AdminUserDto.RoleDto();
        BeanUtils.copyProperties(role, roleDto);
        return roleDto;
    };

    public boolean isExist(String username) {
        User user = findByName(username);
        return null != user;
    }

    public User findByName(String username) {
        return userDAO.findByUsername(username).orElse(null);
    }

    @Transactional
    public UserDto getByName(String username) {
        return userDAO.findByUsername(username).map(user -> {
            UserDto userDto = new UserDto();
            BeanUtils.copyProperties(user, userDto);
            Collection<UserDto.RoleDto> roles = user.getRoles().stream()
                    .map(roleMapper).collect(Collectors.toList());
            userDto.setRoles(roles);
            return userDto;
        }).get();
    }

    public User get(String username, String password) {
        return userDAO.getByUsernameAndPassword(username, password);
    }

    public void add(User user) {
        userDAO.save(user);
    }


    // admin function

    @Transactional
    public List<AdminUserDto> getAdminUsers() {
        return userDAO.findAll().stream().map(user -> {
            AdminUserDto userDto = new AdminUserDto();
            BeanUtils.copyProperties(user, userDto);
            Collection<AdminUserDto.RoleDto> roles = user.getRoles().stream()
                    .map(adminRoleMapper).collect(Collectors.toList());
            userDto.setRoles(roles);
            return userDto;
        }).collect(Collectors.toList());
    }

    public void updateStatus(AdminUserStatusDto adminUserStatusDto) {
        User user = findByName(adminUserStatusDto.getUsername());
        user.setEnabled(adminUserStatusDto.isEnabled());
        userDAO.save(user);
    }

    public void resetPassword(AdminUserPasswordDto adminUserPasswordDto) {
        User user = findByName(adminUserPasswordDto.getUsername());
        user.setPassword(passwordEncoder.encode("123"));
        userDAO.save(user);
    }

    public void updateUser(AdminUserUpdateDto adminUserUpdateDto) {
        User user = findByName(adminUserUpdateDto.getUsername());
        user.setName(adminUserUpdateDto.getName());
        user.setPhone(adminUserUpdateDto.getPhone());
        user.setEmail(adminUserUpdateDto.getEmail());
        List<AdminRole> roles = adminUserUpdateDto.getRoles().stream()
                .map(roleId -> adminRoleService.findById(roleId))
                .collect(Collectors.toList());
        user.setRoles(roles);
        userDAO.save(user);
    }
}
