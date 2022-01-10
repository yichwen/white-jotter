package org.ursprung.wj.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.ursprung.wj.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
}
