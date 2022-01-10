package org.ursprung.wj.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.ursprung.wj.dao.JotterArticleRepository;
import org.ursprung.wj.entity.JotterArticle;
import org.ursprung.wj.utils.MyPage;

@Service
public class JotterArticleService {

    @Autowired
    private JotterArticleRepository jotterArticleDAO;
//    @Autowired
//    private RedisService redisService;

    public MyPage list(int page, int size) {
        MyPage<JotterArticle> articles;
//        String key = "articlepage:" + page;
//        Object articlePageCache = redisService.get(key);

//        if (articlePageCache == null) {
            Sort sort = new Sort(Sort.Direction.DESC, "id");
            Page<JotterArticle> articlesInDb = jotterArticleDAO.findAll(PageRequest.of(page, size, sort));
            articles = new MyPage<>(articlesInDb);
//            redisService.set(key, articles);
//        } else {
//            articles = (MyPage<JotterArticle>) articlePageCache;
//        }
        return articles;
    }

//    用于复现异常
//    @Cacheable(value = RedisConfig.REDIS_KEY_DATABASE)
//    public Page list(int page, int size) {
//        Sort sort = new Sort(Sort.Direction.DESC, "id");
//        return jotterArticleDAO.findAll(PageRequest.of(page, size, sort));
//    }


    public JotterArticle findById(int id) {
        JotterArticle article;
//        String key = "article:" + id;
//        Object articleCache = redisService.get(key);

//        if (articleCache == null) {
            article = jotterArticleDAO.findById(id).get();
//            redisService.set(key, article);
//        } else {
//            article = (JotterArticle) articleCache;
//        }
        return article;
    }

    public void addOrUpdate(JotterArticle article) {
        jotterArticleDAO.save(article);
//        redisService.delete("article" + article.getId());
//        Set<String> keys = redisService.getKeysByPattern("articlepage*");
//        redisService.delete(keys);
    }

    public void delete(int id) {
        jotterArticleDAO.deleteById(id);
//        redisService.delete("article:" + id);
//        Set<String> keys = redisService.getKeysByPattern("articlepage*");
//        redisService.delete(keys);
    }
}
