package koreaIT.service;

import koreaIT.Article;
import koreaIT.dao.ArticleDao;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ArticleService {

    private ArticleDao articleDao = null;
    private List<Article> articleList = new ArrayList<>();

    public ArticleService(Connection conn) {
        this.articleDao = new ArticleDao(conn);
    }

    public List<Article> getArticles() {

        List<Map<String, Object>> articleListMap = articleDao.getArticle();

        for (Map<String, Object> articleMap : articleListMap) {
            Article article = new Article(articleMap);
            articleList.add(article);
        }

        return articleList;
    }

    public Map<String, Object> getArticleById(int id) {
        return articleDao.getArticleById(id);
    }

    public void doDelete(int id) {
        articleDao.doDelete(id);
    }

    public void doModify(int id, String newTitle, String newBody) {
        articleDao.doModify(id, newTitle, newBody);
    }

    public int doWrite(String title, String body) {
        return articleDao.doWrite(title, body);
    }
}