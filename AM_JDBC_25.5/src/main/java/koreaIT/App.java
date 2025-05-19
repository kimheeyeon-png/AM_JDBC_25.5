package koreaIT;

import koreaIT.controller.ArticleController;
import koreaIT.controller.MemberController;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

public class App {

    public void run() {

        System.out.println("== 프로그램 시작 ==");
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.print("명령어) ");
            String cmd = sc.nextLine().trim();

            // DB connection
            Connection conn = null;

            try {
                Class.forName("org.mariadb.jdbc.Driver");
            } catch (ClassNotFoundException e) {
                System.out.println("드라이버 로딩 실패" + e);
            }

            String url = "jdbc:mariadb://127.0.0.1:3306/AM_JDBC_2025_05?useUnicode=true&characterEncoding=utf8&autoReconnect=true&serverTimezone=Asia/Seoul";

            try {
                conn = DriverManager.getConnection(url, "root", "");
                System.out.println("연결 성공!");

                int actionResult = doAction(conn, sc, cmd);

                if (actionResult == -1) {
                    System.out.println("== 프로그램 종료 ==");
                    sc.close();
                    break;
                }
            } catch (SQLException e) {
                System.out.println("에러 : " + e);
            } finally {
                try {
                    if (conn != null && !conn.isClosed()) {
                        conn.close();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            // DB connection 까지

        }

    }

    private int doAction(Connection conn, Scanner sc, String cmd) {
        MemberController memberController = new MemberController(conn, sc);
        ArticleController articleController = new ArticleController(conn, sc);

        if (cmd.equals("exit")) {
            return -1;
        } else if (cmd.equals("member join")) {
            memberController.doJoin();
        } else if (cmd.startsWith("article delete")) {
            articleController.doDelete(cmd);
        } else if (cmd.startsWith("article detail")) {
            articleController.showDetail(cmd);
        } else if (cmd.startsWith("article modify")) {
            articleController.doModify(cmd);
        } else if (cmd.equals("article write")) {
            articleController.doWrite();
        } else if (cmd.equals("article list")) {
            articleController.showList();
        }
        return 0;
    }
}