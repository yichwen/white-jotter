package org.ursprung.wj.dto;

import lombok.Data;

@Data
public class AdminPermissionDto {
    private int id;
    private String name;
    private String description;
    private String url;
}
