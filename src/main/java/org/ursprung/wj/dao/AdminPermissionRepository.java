package org.ursprung.wj.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.ursprung.wj.entity.AdminPermission;

public interface AdminPermissionRepository extends JpaRepository<AdminPermission, Integer> {
}
