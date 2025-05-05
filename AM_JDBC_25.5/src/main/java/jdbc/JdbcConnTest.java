package jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JdbcConnTest {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/test";
        String user = "root";
        String password = "";

        // JDBC 드라이버 로드
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch(ClassNotFoundException e) {
            System.out.println("JDBC 드라이버 로드하는데 문제 발생 : " + e.getMessage());
            e.printStackTrace();
        }

        // 접속
        Connection con = null;

        try {
            con = DriverManager.getConnection(url, user, password);
            System.out.println("연결 완료");
        } catch (SQLException e) {
            System.out.println("연결 오류 : " + e.getMessage());
            e.printStackTrace();
        }

        // 접속 종료
        try {
            if (con != null) con.close();
        } catch (SQLException e) {}
    }
}