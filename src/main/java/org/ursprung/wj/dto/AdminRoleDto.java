package org.ursprung.wj.dto;

import lombok.Data;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;

@Data
public class AdminRoleDto {

    private int id;
    private String name;
    private String nameZh;
    private boolean enabled;
    private Collection<PermissionDto> perms = new ArrayList<>();
    private Collection<MenuDto> menus = new ArrayList<>();

    @Data
    public static class PermissionDto {
        private int id;
        private String name;
        private String description;
        private String url;
    }

    @Data
    public static class MenuDto {
        private int id;
        private String path;
        private String name;
        private String nameZh;
        private String iconCls;
        private String component;
        private int parentId;
    }

}
