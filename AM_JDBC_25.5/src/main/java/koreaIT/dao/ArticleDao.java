package koreaIT.dao;

import util.DBUtil;
import util.SecSql;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

public class ArticleDao {

    private Connection conn = null;

    public ArticleDao(Connection conn) {
        this.conn = conn;
    }

    public List<Map<String, Object>> getArticle() {
        SecSql sql = new SecSql();
        sql.append("SELECT *");
        sql.append("FROM `article`");
        sql.append("ORDER BY `id` DESC;");

        List<Map<String, Object>> articleListMap = DBUtil.selectRows(conn, sql);

        return articleListMap;
    }

    public Map<String, Object> getArticleById(int id) {
        SecSql sql = new SecSql();
        sql.append("SELECT *");
        sql.append("FROM `article`");
        sql.append("WHERE `id` = ?", id);

        return DBUtil.selectRow(conn, sql);
    }

    public void doDelete(int id) {
        SecSql sql = new SecSql();
        sql.append("DELETE FROM `article`");
        sql.append("WHERE `id` = ?", id);

        DBUtil.delete(conn, sql);
    }

    public void doModify(int id, String newTitle, String newBody) {
        SecSql sql = new SecSql();
        sql.append("UPDATE `article`");
        sql.append("SET `updateDate` = NOW()");
        if (newTitle.length() > 0) {
            sql.append(", `title` = ?", newTitle);
        }
        if (newBody.length() > 0) {
            sql.append(", `body` = ?", newBody);
        }
        sql.append("WHERE `id` = ?", id);

        DBUtil.update(conn, sql);
    }

    public int doWrite(String title, String body) {
        SecSql sql = new SecSql();

        sql.append("INSERT INTO `article`");
        sql.append("SET `regDate` = NOW(),");
        sql.append("`updateDate` = NOW(),");
        sql.append("`title` = ?,", title);
        sql.append("`body` = ?;", body);

        int id = DBUtil.insert(conn, sql);
        return id;
    }
}