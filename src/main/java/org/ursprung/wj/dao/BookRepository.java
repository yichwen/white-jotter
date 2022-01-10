package org.ursprung.wj.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.ursprung.wj.entity.Book;
import org.ursprung.wj.entity.Category;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Integer> {
    List<Book> findAllByCategory(Category category);
    List<Book> findAllByTitleLikeOrAuthorLike(String keyword1, String keyword2);
}
