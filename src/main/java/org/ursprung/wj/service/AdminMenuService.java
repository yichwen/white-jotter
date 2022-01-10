package org.ursprung.wj.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.ursprung.wj.dao.AdminMenuRepository;
import org.ursprung.wj.dto.AdminMenuDto;
import org.ursprung.wj.entity.AdminMenu;
import org.ursprung.wj.entity.User;
import org.ursprung.wj.utils.Utils;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class AdminMenuService {

    @Autowired
    private UserService userService;

    @Autowired
    private AdminMenuRepository adminMenuRepository;

    private static final Function<AdminMenu, AdminMenuDto> mapper = (menu) -> {
        AdminMenuDto menuDto = new AdminMenuDto();
        BeanUtils.copyProperties(menu, menuDto);
        return menuDto;
    };

    public List<AdminMenuDto> getAdminMenus() {
        return handleMenus(new HashSet<>(adminMenuRepository.findAll()));
    }

    @Transactional
    public List<AdminMenuDto> getMenusByCurrentUser() {
        String username = (String) Utils.getCurrentUser();
        // get user
        User user = userService.findByName(username);
        // get role ids
        Set<AdminMenu> menus = user.getRoles().stream()
                .flatMap(role -> role.getMenus().stream())
                .collect(Collectors.toSet());
        // Adjust the structure of the menu.
        return handleMenus(menus);
    }

    private List<AdminMenuDto> handleMenus(Set<AdminMenu> menus) {
        List<AdminMenuDto> parents = menus.stream().filter(menu -> menu.getParentId() == 0)
                .sorted(Comparator.comparingInt(AdminMenu::getSort))
                .map(mapper).distinct().collect(Collectors.toList());
        parents.forEach(m -> {
            Set<AdminMenuDto> children = getAllByParentId(menus, m.getId()).stream()
                    .map(mapper).collect(Collectors.toSet());
            m.setChildren(children);
        });
        return parents;
    }

    private List<AdminMenu> getAllByParentId(Set<AdminMenu> menus, int parentId) {
        return menus.stream().filter(menu -> menu.getParentId() == parentId)
                .sorted(Comparator.comparingInt(AdminMenu::getSort))
                .distinct()
                .collect(Collectors.toList());
    }

    public AdminMenu findById(Integer id) {
        return adminMenuRepository.findById(id).orElseThrow(() -> {
            String message = String.format("permission id [%d] is not found", id);
            return new IllegalStateException(message);
        });
    }

}
