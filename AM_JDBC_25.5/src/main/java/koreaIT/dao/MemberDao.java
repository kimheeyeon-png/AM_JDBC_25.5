package koreaIT.dao;

import util.DBUtil;
import util.SecSql;

import java.sql.Connection;

public class MemberDao {
    public boolean isLoginJoin(Connection conn, String loginId) {

        // DB에 있는 아이디 중복체크
        SecSql sql = new SecSql();

        sql.append("SELECT COUNT(*) > 0");
        sql.append("FROM `member`");
        sql.append("WHERE `loginId` = ?;", loginId);

        return DBUtil.selectRowBooleanValue(conn, sql);
    }
}