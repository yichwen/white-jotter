package org.ursprung.wj.dto;

import lombok.Data;

@Data
public class AdminUserStatusDto {
    private String username;
    private boolean enabled;
}
