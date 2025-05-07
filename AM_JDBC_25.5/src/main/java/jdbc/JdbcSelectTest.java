package jdbc;

import koreaIT.Article;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JdbcSelectTest {
    public static void main(String[] args) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        List<Article> articleList = new ArrayList<>();

        try {
            Class.forName("org.mariadb.jdbc.Driver");
            String url = "jdbc:mariadb://127.0.0.1:3306/AM_JDBC_2025_05?useUnicode=true&characterEncoding=utf8&autoReconnect=true&serverTimezone=Asia/Seoul";
            conn = DriverManager.getConnection(url, "root", "");
            System.out.println("연결 성공!");

            // select 테스트

            String sql = "SELECT *";
            sql += "FROM `article`;";

            System.out.println(sql);

            pstmt = conn.prepareStatement(sql);

            rs = pstmt.executeQuery();
    //      System.out.println(rs.next());

            while(rs.next()){
                int id = rs.getInt("id");
                String regDate = rs.getString("regDate");
                String updateDate = rs.getString("updateDate");
                String title = rs.getString("title");
                String body = rs.getString("body");


                Article article = new Article(id, regDate, updateDate, title, body);

                System.out.printf("%d, %s, %s, %s, %s\n", article.getId(), article.getRegDate(), article.getUpdateDate(), article.getTitle(), article.getBody());

                articleList.add(article);

            }

        } catch (ClassNotFoundException e) {
            System.out.println("드라이버 로딩 실패" + e);
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
            try {
                if (pstmt != null && !pstmt.isClosed()) {
                    pstmt.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                if (rs != null && !rs.isClosed()) {
                    rs.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}