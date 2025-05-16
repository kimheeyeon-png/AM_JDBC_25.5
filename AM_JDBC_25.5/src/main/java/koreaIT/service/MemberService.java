package koreaIT.service;

import koreaIT.dao.MemberDao;

import java.sql.Connection;

public class MemberService {

    private MemberDao memberDao = new MemberDao();

    public boolean isLoginJoin(Connection conn, String loginId) {
        return memberDao.isLoginJoin(conn, loginId);
    }
}