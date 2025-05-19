package koreaIT.service;

import koreaIT.dao.MemberDao;

import java.sql.Connection;

public class MemberService {

    private MemberDao memberDao = null;

    public MemberService(Connection conn) {
        this.memberDao = new MemberDao(conn);
    }

    public boolean isLoginJoinable(String loginId) {
        return memberDao.isLoginJoinable(loginId);
    }

    public int doJoin(String loginId, String loginPw, String name) {
        return memberDao.doJoin(loginId, loginPw, name);
    }
}