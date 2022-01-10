package org.ursprung.wj.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.ursprung.wj.dao.BookRepository;
import org.ursprung.wj.entity.Book;
import org.ursprung.wj.entity.Category;

import java.util.List;

@Service
public class BookService {

    @Autowired
    private BookRepository bookDAO;

    @Autowired
    private CategoryService categoryService;

    // 查出所有书籍
    public List<Book> list() {
        Sort sort = new Sort(Sort.Direction.DESC, "id");
        return bookDAO.findAll(sort);
    }

    // 增加或更新书籍
    public void addOrUpdate(Book book) {
        bookDAO.save(book);
    }

    // 通过 id 删除书籍
    public void deleteById(int id) {
        bookDAO.deleteById(id);
    }

    // 通过分类查出书籍
    public List<Book> listByCategory(int cid) {
        Category category = categoryService.get(cid);
        return bookDAO.findAllByCategory(category);
    }

    // 通过关键字查出书籍，关键字搜索用于作者和书名
    public List<Book> search(String keywords) {
        return bookDAO.findAllByTitleLikeOrAuthorLike("%" + keywords + "%", "%" + keywords + "%");
    }

}
