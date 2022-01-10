package org.ursprung.wj.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.ursprung.wj.dao.AdminPermissionRepository;
import org.ursprung.wj.dto.AdminPermissionDto;
import org.ursprung.wj.entity.AdminPermission;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdminPermissionService {

    @Autowired
    private AdminPermissionRepository adminPermissionRepository;

    public List<AdminPermissionDto> getAdminPermissions() {
        return adminPermissionRepository.findAll().stream()
                .map(permission -> {
                    AdminPermissionDto permissionDto = new AdminPermissionDto();
                    BeanUtils.copyProperties(permission, permissionDto);
                    return permissionDto;
                })
                .collect(Collectors.toList());
    }

    public AdminPermission findById(Integer id) {
        return adminPermissionRepository.findById(id).orElseThrow(() -> {
            String message = String.format("permission id [%d] is not found", id);
            return new IllegalStateException(message);
        });
    }

}
