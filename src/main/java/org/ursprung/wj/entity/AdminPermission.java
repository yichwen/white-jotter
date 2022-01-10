package org.ursprung.wj.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Collection;

@Data
@Entity
@Table(name = "admin_permission")
public class AdminPermission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    /**
     * Permission name;
     */
    private String name;

    /**
     * Permission's description(in Chinese)
     */
    private String description;

    /**
     * The path which triggers permission check.
     */
    private String url;

    @ManyToMany(mappedBy = "perms")
    private Collection<AdminRole> roles;

}
