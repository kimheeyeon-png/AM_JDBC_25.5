package koreaIT.dao;

import koreaIT.container.Container;
import util.DBUtil;
import util.SecSql;

import java.sql.Connection;
import java.util.Map;

public class MemberDao {

    private Connection conn;

    public MemberDao() {
        this.conn = Container.conn;
    }

    public boolean isLoginJoinable(String loginId) {

        // DB에 있는 아이디 중복체크
        SecSql sql = new SecSql();

        sql.append("SELECT COUNT(*) > 0");
        sql.append("FROM `member`");
        sql.append("WHERE `loginId` = ?;", loginId);

        return DBUtil.selectRowBooleanValue(conn, sql);
    }

    public int doJoin(String loginId, String loginPw, String name) {
        SecSql sql = new SecSql();

        sql.append("INSERT INTO `member`");
        sql.append("SET `regDate` = NOW(),");
        sql.append("`updateDate` = NOW(),");
        sql.append("`loginId` = ?,", loginId);
        sql.append("`loginPw` = ?,", loginPw);
        sql.append("`name` = ?;", name);

        int id = DBUtil.insert(conn, sql);
        return id;
    }

    public Map<String, Object> getMemberByLoginId(String loginId) {
        SecSql sql = new SecSql();

        sql.append("SELECT *");
        sql.append("FROM `member`");
        sql.append("WHERE `loginId` = ?;", loginId);

        Map<String, Object> memberMap =  DBUtil.selectRow(conn, sql);

        return memberMap;
    }
}