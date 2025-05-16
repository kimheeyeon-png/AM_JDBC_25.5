package koreaIT.service;

import koreaIT.dao.ArticleDao;

import java.sql.Connection;
import java.util.Map;

public class ArticleService {

    private Connection conn = null;
    private ArticleDao articleDao = null;

    public ArticleService(Connection conn) {
        this.conn = conn;
        this.articleDao = new ArticleDao(conn);
    }

    public Map<String, Object> getArticleById(int id) {
        return articleDao.getArticleById(id);
    }

    public void doDelete(int id) {
        articleDao.doDelete(id);
    }
}