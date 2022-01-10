package org.ursprung.wj.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.ursprung.wj.dao.CategoryRepository;
import org.ursprung.wj.entity.Category;

import java.util.List;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryDAO;

    public List<Category> list() {
        Sort sort = new Sort(Sort.Direction.DESC, "id");
        return categoryDAO.findAll(sort);
    }

    public Category get(int id) {
        Category c = categoryDAO.findById(id).orElse(null);
        return c;
    }

}
