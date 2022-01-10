package org.ursprung.wj.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.ursprung.wj.entity.AdminMenu;

public interface AdminMenuRepository extends JpaRepository<AdminMenu, Integer> {
}
