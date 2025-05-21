package koreaIT.container;

import koreaIT.controller.ArticleController;
import koreaIT.controller.MemberController;
import koreaIT.dao.ArticleDao;
import koreaIT.dao.MemberDao;
import koreaIT.service.ArticleService;
import koreaIT.service.MemberService;
import koreaIT.session.Session;

import java.sql.Connection;
import java.util.Scanner;

public class Container {

    public static Session session;
    public static Scanner sc;
    public static Connection conn;

    public static ArticleController articleController;
    public static MemberController memberController;

    public static ArticleService articleService;
    public static MemberService memberService;

    public static ArticleDao articleDao;
    public static MemberDao memberDao;

    public static void init() {
        session = new Session();
        sc = new Scanner(System.in);

        articleDao = new ArticleDao();
        memberDao = new MemberDao();

        articleService = new ArticleService();
        memberService = new MemberService();

        articleController = new ArticleController();
        memberController = new MemberController();
    }
}
