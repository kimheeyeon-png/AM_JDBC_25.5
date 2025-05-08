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
        } else if (cmd.startsWith("article modify")) {
            int id = 0;

            //parsing
            try {
                id = Integer.parseInt(cmd.split(" ")[2]);
            } catch (Exception e) {
                System.out.println("정수 입력하세요");
            }

            System.out.println("== 글 수정 ==");
            System.out.print("새 제목 : ");
            String newTitle = sc.nextLine().trim();
            System.out.print("새 내용 : ");
            String newBody = sc.nextLine().trim();

            // DB update
            PreparedStatement pstmt = null;

            try {
                String sql = "UPDATE `article` ";
                sql += "SET `updateDate` = NOW()";
                if (newTitle.length() > 0) {
                    sql += ", `title` = '" + newTitle + "'";
                }
                if (newBody.length() > 0) {
                    sql += ", `body` = '" + newBody + "'";
                }
                sql += "WHERE `id` = " + id + ";";

                System.out.println(sql);

                pstmt = conn.prepareStatement(sql);

                pstmt.executeUpdate();
            } catch (SQLException e) {
                System.out.println("에러2 update");
            } finally {
                try {
                    if (pstmt != null && !pstmt.isClosed()) {
                        pstmt.close();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            // DB update 까지


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

//            // DB insert
//            PreparedStatement pstmt = null;
//
//            try {
//                String sql = "INSERT INTO `article` ";
//                sql += "SET `regDate` = NOW(),";
//                sql += "`updateDate` = NOW(),";
//                sql += "`title` = '" + title + "',";
//                sql += "`body` = '" + body + "';";
//
//                System.out.println(sql);
//
//                pstmt = conn.prepareStatement(sql);
//
//                int affectedRows = pstmt.executeUpdate();
//                System.out.println(affectedRows + "열에 적용됨");
//
//            } catch (SQLException e) {
//                System.out.println("에러1 insert");
//            } finally {
//                try {
//                    if (pstmt != null && !pstmt.isClosed()) {
//                        pstmt.close();
//                    }
//                } catch (SQLException e) {
//                    e.printStackTrace();
//                }
//            }
//        // DB insert 까지


        } else if (cmd.equals("article list")) {
            List<Article> articleList = new ArrayList<>();

            SecSql sql = new SecSql();
            sql.append("SELECT *");
            sql.append("FROM `article`");
            sql.append("ORDER BY `id` DESC;");

            List<Map<String, Object>> articleListMap = DBUtil.selectRows(conn, sql);

            for(Map<String, Object> articleMap : articleListMap){
                Article article = new Article(articleMap);
                articleList.add(article);
            }

            // DB select

//            PreparedStatement pstmt = null;
//            ResultSet rs = null;
//
//            try {
//                String sql = "SELECT *";
//                sql += "FROM `article`";
//                sql += "ORDER BY `id` DESC;";
//
//                System.out.println(sql);
//
//                pstmt = conn.prepareStatement(sql);
//
//                rs = pstmt.executeQuery();
//
//                while (rs.next()) {
//                    int id = rs.getInt("id");
//                    String regDate = rs.getString("regDate");
//                    String updateDate = rs.getString("updateDate");
//                    String title = rs.getString("title");
//                    String body = rs.getString("body");
//
//                    Article article = new Article(id, regDate, updateDate, title, body);
//
//                    articleList.add(article);
//
//                }
//
//            } catch (SQLException e) {
//                System.out.println("에러3 select");
//            } finally {
//                try {
//                    if (pstmt != null && !pstmt.isClosed()) {
//                        pstmt.close();
//                    }
//                } catch (SQLException e) {
//                    e.printStackTrace();
//                }
//                try {
//                    if (rs != null && !rs.isClosed()) {
//                        rs.close();
//                    }
//                } catch (SQLException e) {
//                    e.printStackTrace();
//                }
//            }
            // DB select 까지
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