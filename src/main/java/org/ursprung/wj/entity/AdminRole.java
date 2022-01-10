package org.ursprung.wj.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Collection;

@Data
@Entity
@Table(name = "admin_role")
public class AdminRole {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    /**
     * Role name.
     */
    private String name;

    /**
     * Role name in Chinese.
     */
    @Column(name = "name_zh")
    private String nameZh;

    /**
     * Role status.
     */
    private boolean enabled;

    /**
     * Transient property for storing permissions owned by current role.
     */
    @ManyToMany
    @JoinTable(name = "admin_role_permission", joinColumns = @JoinColumn(name = "rid"), inverseJoinColumns = @JoinColumn(name = "pid"))
    private Collection<AdminPermission> perms;

    /**
     * Transient property for storing menus owned by current role.
     */
    @ManyToMany
    @JoinTable(name = "admin_role_menu", joinColumns = @JoinColumn(name = "rid"), inverseJoinColumns = @JoinColumn(name = "mid"))
    private Collection<AdminMenu> menus;

}
