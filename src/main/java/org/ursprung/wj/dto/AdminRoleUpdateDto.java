package org.ursprung.wj.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.Collection;

@Data
public class AdminRoleUpdateDto {
    private int id;
    private String name;
    private String nameZh;
    private boolean enabled;
    private Collection<Integer> perms = new ArrayList<>();
    private Collection<Integer> menus = new ArrayList<>();
}
