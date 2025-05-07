package koreaIT;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        int lastId = 0;
        List<Article> articleList = new ArrayList<>();

        System.out.println("== 프로그램 시작 ==");

        while(true){
            System.out.print("명령어) ");
            String cmd = sc.nextLine().trim();

            if(cmd.equals("exit")){
                System.out.println("== 프로그램 종료 ==");
                break;
            }
            else if (cmd.equals("article write")){
                System.out.print("제목 : ");
                String title = sc.nextLine().trim();
                System.out.print("내용 : ");
                String body = sc.nextLine().trim();
                lastId++;

    //            Article addArticle = new Article(lastId, title, body);
    //            articleList.add(addArticle);


                // DB insert
                Connection conn = null;
                PreparedStatement pstmt = null;

                try {
                    Class.forName("org.mariadb.jdbc.Driver");
                    String url = "jdbc:mariadb://127.0.0.1:3306/AM_JDBC_2025_05?useUnicode=true&characterEncoding=utf8&autoReconnect=true&serverTimezone=Asia/Seoul";
                    conn = DriverManager.getConnection(url, "root", "");
                    System.out.println("연결 성공!");

                    // Insert 테스트

                    String sql = "INSERT INTO `article` ";
                    sql += "SET `regDate` = NOW(),";
                    sql += "`updateDate` = NOW(),";
                    sql += "`title` = '" + title + "',";
                    sql += "`body` = '" + body + "';";

                    System.out.println(sql);

                    pstmt = conn.prepareStatement(sql);

                    int affectedRows = pstmt.executeUpdate();
                    System.out.println("affected rows : " + affectedRows);


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
                }
                // DB insert 까지


                System.out.printf("%d번 게시글이 등록되었습니다.\n", lastId);
            }
            else if (cmd.equals("article list")){
                System.out.println("번호    /     제목");
                for(int i = articleList.size() - 1; i >= 0; i--){
                    System.out.printf("%d      /   %s\n", articleList.get(i).getId(), articleList.get(i).getTitle());
                }
            }
        }
    }
}