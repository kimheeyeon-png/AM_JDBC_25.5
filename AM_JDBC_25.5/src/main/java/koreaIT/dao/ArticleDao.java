package koreaIT.dao;

import koreaIT.container.Container;
import util.DBUtil;
import util.SecSql;

import java.util.List;
import java.util.Map;

public class ArticleDao {

    public ArticleDao() {
    }

    public List<Map<String, Object>> getArticles() {
        SecSql sql = new SecSql();
        sql.append("SELECT *");
        sql.append("FROM `article` a");
        sql.append("INNER JOIN `member` m");
        sql.append("ON a.memberId = m.id");
        sql.append("ORDER BY a.`id` DESC;");

        List<Map<String, Object>> articleListMap = DBUtil.selectRows(Container.conn, sql);

//        DB에서 어떤 데이터 왔는지 확인
//        for (Map<String, Object> articleMap : articleListMap){
//            System.out.println(articleMap.toString());
//        }

        return articleListMap;
    }

    public Map<String, Object> getArticleById(int id) {
        SecSql sql = new SecSql();
        sql.append("SELECT *");
        sql.append("FROM `article` a");
        sql.append("INNER JOIN `member` m");
        sql.append("ON a.memberId = m.id");
        sql.append("WHERE a.`id` = ?", id);

        return DBUtil.selectRow(Container.conn, sql);
    }

    public void doDelete(int id) {
        SecSql sql = new SecSql();
        sql.append("DELETE FROM `article`");
        sql.append("WHERE `id` = ?", id);

        DBUtil.delete(Container.conn, sql);
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

        DBUtil.update(Container.conn, sql);
    }

    public int doWrite(String title, String body) {
        SecSql sql = new SecSql();

        sql.append("INSERT INTO `article`");
        sql.append("SET `regDate` = NOW(),");
        sql.append("`updateDate` = NOW(),");
        sql.append("`member` = ?,", Container.session.loginedMember);
        sql.append("`title` = ?,", title);
        sql.append("`body` = ?;", body);

        int id = DBUtil.insert(Container.conn, sql);
        return id;
    }
}