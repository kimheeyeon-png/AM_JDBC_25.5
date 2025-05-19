package koreaIT.controller;

import koreaIT.service.MemberService;
import util.DBUtil;
import util.SecSql;

import java.sql.Connection;
import java.util.Scanner;

public class MemberController {
    private MemberService memberService = new MemberService();

    private Connection conn = null;
    private Scanner sc = null;

    public MemberController(Connection conn, Scanner sc) {
        this.conn = conn;
        this.sc = sc;
    }

    public void doJoin() {

        String loginId = null;
        String loginPw = null;
        String name = null;

        while (true) {
            System.out.print("새 아이디 : ");
            loginId = sc.nextLine().trim();
            if (loginId.length() == 0 || loginId.contains(" ")) {
                System.out.println("아이디 똑바로 입력하시오.");
                continue;
            }

            boolean isLoginJoin = memberService.isLoginJoin(conn, loginId);

            if (isLoginJoin) {
                System.out.println(loginId + "는(은) 이미 사용중입니다.");
                continue;
            }
            break;

        }
        while (true) {
            System.out.print("새 비밀번호 : ");
            loginPw = sc.nextLine();

            if (loginPw.length() == 0 || loginPw.contains(" ")) {
                System.out.println("비밀번호 똑바로 입력하시오.");
                continue;
            }
            System.out.print("비밀번호 확인 : ");
            String loginPwCheck = sc.nextLine();
            if (loginPw.equals(loginPwCheck) == false) {
                System.out.println("비밀번호가 일치하지 않습니다.");
                continue;
            }
            break;
        }

        while (true) {
            System.out.print("새 이름 : ");
            name = sc.nextLine().trim();
            if (name.length() == 0 || name.contains(" ")) {
                System.out.println("이름 똑바로 입력하시오.");
                continue;
            }
            break;
        }

        // DB insert
        SecSql sql = new SecSql();

        sql.append("INSERT INTO `member`");
        sql.append("SET `regDate` = NOW(),");
        sql.append("`updateDate` = NOW(),");
        sql.append("`loginId` = ?,", loginId);
        sql.append("`loginPw` = ?,", loginPw);
        sql.append("`name` = ?;", name);

        int id = DBUtil.insert(conn, sql);

        // DB insert 까지

        System.out.println(id + "번 회원 생성되었습니다.");
    }
}