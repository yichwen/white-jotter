package org.ursprung.wj.dto;

import lombok.Data;

import java.util.Collection;

@Data
public class UserDto {

    private Integer id;
    private String username;
    private String password;
    private String salt;
    private String name;
    private String phone;
    private String email;
    private boolean enabled;
    private Collection<RoleDto> roles;

    @Data
    public static class RoleDto {
        private Integer id;
        private String name;
    }

}
