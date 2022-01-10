package org.ursprung.wj.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.ursprung.wj.dao.AdminRoleRepository;
import org.ursprung.wj.dto.AdminRoleCreateDto;
import org.ursprung.wj.dto.AdminRoleDto;
import org.ursprung.wj.dto.AdminRoleStatusDto;
import org.ursprung.wj.dto.AdminRoleUpdateDto;
import org.ursprung.wj.entity.AdminMenu;
import org.ursprung.wj.entity.AdminPermission;
import org.ursprung.wj.entity.AdminRole;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class AdminRoleService {

    @Autowired
    private AdminRoleRepository adminRoleRepository;

    @Autowired
    private AdminPermissionService adminPermissionService;

    @Autowired
    private AdminMenuService adminMenuService;

    private static final Function<AdminMenu, AdminRoleDto.MenuDto> menuMapper = (menu) -> {
        AdminRoleDto.MenuDto menuDto = new AdminRoleDto.MenuDto();
        BeanUtils.copyProperties(menu, menuDto);
        return menuDto;
    };

    private static final Function<AdminPermission, AdminRoleDto.PermissionDto> permMapper = (perm) -> {
        AdminRoleDto.PermissionDto permDto = new AdminRoleDto.PermissionDto();
        BeanUtils.copyProperties(perm, permDto);
        return permDto;
    };

    @Transactional
    public List<AdminRoleDto> getAdminRoles() {
        return adminRoleRepository.findAll().stream()
                .map(role -> {
                    AdminRoleDto roleDto = new AdminRoleDto();
                    BeanUtils.copyProperties(role, roleDto, "perms", "menus");
                    roleDto.setMenus(role.getMenus().stream().map(menuMapper).collect(Collectors.toList()));
                    roleDto.setPerms(role.getPerms().stream().map(permMapper).collect(Collectors.toList()));
                    return roleDto;
                })
                .collect(Collectors.toList());
    }


    public void updateRoleStatus(AdminRoleStatusDto adminRoleStatusDto) {
        AdminRole adminRole = findById(adminRoleStatusDto.getId());
        adminRole.setEnabled(adminRoleStatusDto.isEnabled());
        adminRoleRepository.save(adminRole);
    }

    public AdminRole findById(Integer id) {
        return adminRoleRepository.findById(id).orElseThrow(() -> {
            String message = String.format("role id [%d] is not found", id);
            return new IllegalStateException(message);
        });
    }

    public void addRole(AdminRoleCreateDto adminRoleCreateDto) {
        AdminRole adminRole = new AdminRole();
        BeanUtils.copyProperties(adminRoleCreateDto, adminRole);
        adminRoleRepository.save(adminRole);
    }

    public void updateRole(AdminRoleUpdateDto adminRoleUpdateDto) {
        AdminRole adminRole = findById(adminRoleUpdateDto.getId());
        adminRole.setName(adminRoleUpdateDto.getName());
        adminRole.setNameZh(adminRoleUpdateDto.getNameZh());

        List<AdminPermission> perms = adminRoleUpdateDto.getPerms().stream()
                .map(permId -> adminPermissionService.findById(permId))
                .collect(Collectors.toList());
        adminRole.setPerms(perms);

        List<AdminMenu> menus = adminRoleUpdateDto.getMenus().stream()
                .map(menuId -> adminMenuService.findById(menuId))
                .collect(Collectors.toList());
        adminRole.setMenus(menus);

        adminRoleRepository.save(adminRole);
    }

}
