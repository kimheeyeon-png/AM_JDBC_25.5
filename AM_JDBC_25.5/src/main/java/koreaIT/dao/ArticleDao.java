package koreaIT.dao;

import util.DBUtil;
import util.SecSql;

import java.sql.Connection;
import java.util.Map;

public class ArticleDao {

    private Connection conn = null;

    public ArticleDao(Connection conn) {
        this.conn = conn;
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
}