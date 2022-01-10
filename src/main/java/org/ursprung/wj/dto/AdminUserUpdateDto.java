package org.ursprung.wj.dto;

import lombok.Data;

import java.util.Collection;

@Data
public class AdminUserUpdateDto {
    private String username;
    private String name;
    private String phone;
    private String email;
    private Collection<Integer> roles;
}
