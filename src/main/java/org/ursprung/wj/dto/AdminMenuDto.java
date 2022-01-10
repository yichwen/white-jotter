package org.ursprung.wj.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.Collection;

@Data
public class AdminMenuDto {
    private int id;
    private String path;
    private String name;
    private String nameZh;
    private String iconCls;
    private String component;
    private int parentId;
    private Collection<AdminMenuDto> children = new ArrayList<>();
}
