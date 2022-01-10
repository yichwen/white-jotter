package org.ursprung.wj.entity;

import lombok.Data;
import javax.persistence.*;
import java.util.Collection;

@Data
@Entity
@Table(name = "admin_menu")
public class AdminMenu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    /**
     * Menu access path.
     */
    private String path;

    /**
     * Menu name.
     */
    private String name;

    /**
     * Menu name in Chinese.
     */
    private String nameZh;

    /**
     * Menu icon class(use element-ui icons).
     */
    private String iconCls;

    /**
     * Front-end component name corresponding to menu.
     */
    private String component;

    /**
     * Parent menu.
     */
    private int parentId;

    /**
     * Sorting
     */
    private int sort;

    @ManyToMany(mappedBy = "menus")
    private Collection<AdminRole> roles;

}
