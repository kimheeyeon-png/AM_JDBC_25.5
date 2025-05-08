package koreaIT;

import util.DBUtil;
import util.SecSql;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class App {

    public void run() {

        System.out.println("== 프로그램 시작 ==");
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.print("명령어) ");
            String cmd = sc.nextLine().trim();

            // DB conntion
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
        if (cmd.equals("exit")) {
            return -1;

        }
        else if (cmd.startsWith("article delete")){
            int id = 0;

            //parsing
            try {
                id = Integer.parseInt(cmd.split(" ")[2]);
            } catch (Exception e) {
                System.out.println("정수 입력하세요");
            }
            //parsing 까지

            // 글 유무체크
            SecSql sql = new SecSql();
            sql.append("SELECT *");
            sql.append("FROM `article`");
            sql.append("WHERE `id` = ?", id);

            Map<String, Object> articleMap = DBUtil.selectRow(conn, sql);
            if(articleMap.isEmpty()){
                System.out.printf("%d번 글은 없습니다.\n", id);
                return 0;
            }
            // 글 유무체크 끝

            sql = new SecSql();
            sql.append("DELETE FROM `article`");
            sql.append("WHERE `id` = ?", id);

            DBUtil.delete(conn, sql);

            System.out.printf("%d번 게시글이 삭제되었습니다.\n", id);

        }
        else if(cmd.startsWith("article detail")){
            int id = 0;

            //parsing
            try {
                id = Integer.parseInt(cmd.split(" ")[2]);
            } catch (Exception e) {
                System.out.println("정수 입력하세요");
            }
            //parsing 까지

            // 글 유무체크
            SecSql sql = new SecSql();
            sql.append("SELECT *");
            sql.append("FROM `article`");
            sql.append("WHERE `id` = ?", id);

            Map<String, Object> articleMap = DBUtil.selectRow(conn, sql);
            if(articleMap.isEmpty()){
                System.out.printf("%d번 글은 없습니다.\n", id);
                return 0;
            }
            // 글 유무체크 끝

            System.out.println("== 상세보기 ==");

            Article article = new Article(articleMap);

            System.out.println("번호 : " + article.getId());
            System.out.println("등록날짜 : " +  article.getRegDate());
            System.out.println("수정날짜 : " +  article.getUpdateDate());
            System.out.println("제목 : " +  article.getTitle());
            System.out.println("내용 : " +  article.getBody());

        }
        else if (cmd.startsWith("article modify")) {
            int id = 0;

            //parsing
            try {
                id = Integer.parseInt(cmd.split(" ")[2]);
            } catch (Exception e) {
                System.out.println("정수 입력하세요");
            }
            //parsing 까지

            // 글 유무체크
            SecSql sql = new SecSql();
            sql.append("SELECT *");
            sql.append("FROM `article`");
            sql.append("WHERE `id` = ?", id);

            Map<String, Object> articleMap = DBUtil.selectRow(conn, sql);
            if(articleMap.isEmpty()){
                System.out.printf("%d번 글은 없습니다.\n", id);
                return 0;
            }

            // 글 유무체크 끝

            System.out.println("== 글 수정 ==");
            System.out.print("새 제목 : ");
            String newTitle = sc.nextLine().trim();
            System.out.print("새 내용 : ");
            String newBody = sc.nextLine().trim();

            // DB update
            sql = new SecSql();
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
            // DB update 까지

            System.out.printf("%d번 게시글이 수정되었습니다.\n", id);
            
        } else if (cmd.equals("article write")) {
            System.out.print("제목 : ");
            String title = sc.nextLine().trim();
            System.out.print("내용 : ");
            String body = sc.nextLine().trim();

            // DB insert
            SecSql sql = new SecSql();

            sql.append("INSERT INTO `article`");
            sql.append("SET `regDate` = NOW(),");
            sql.append("`updateDate` = NOW(),");
            sql.append("`title` = ?,", title);
            sql.append("`body` = ?;", body);

            int id = DBUtil.insert(conn, sql);

            // DB insert 까지

            System.out.printf("%d번 게시글이 등록되었습니다.\n", id);

        } else if (cmd.equals("article list")) {

            List<Article> articleList = new ArrayList<>();

            SecSql sql = new SecSql();
            sql.append("SELECT *");
            sql.append("FROM `article`");
            sql.append("ORDER BY `id` DESC;");

            List<Map<String, Object>> articleListMap = DBUtil.selectRows(conn, sql);

            for (Map<String, Object> articleMap : articleListMap) {
                Article article = new Article(articleMap);
                articleList.add(article);
            }

            if (articleList.size() == 0) {
                System.out.println("게시글이 없습니다.");
                return 0;
            }

            System.out.println("번호    /     제목");
            for (Article article : articleList) {
                System.out.printf("%d      /   %s\n", article.getId(), article.getTitle());
            }
        }
        return 0;
    }
}