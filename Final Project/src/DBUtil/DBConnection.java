package DBUtil;

import java.sql.*;

/**
 * @author : Ethan Zhang
 * @description :
 * @createTime : [2021/4/25 0:04]
 * @updateTime : [2021/4/25 0:04]
 */
public class DBConnection {

    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    static final String DEFAULT_DB_URL = "jdbc:mysql://localhost:3306/sys?useUnicode=true&characterEncoding=utf8&serverTimezone=UTC";
    static final String DB_URL = "jdbc:mysql://localhost:3306/LMSDB?useSSL=false&serverTimezone=UTC";

    // user and password
    static final String USER = "root";
    static final String PASS = "123456";

    public static Connection getConnection(String url) {
        Connection conn = null;

        try {
            Class.forName(JDBC_DRIVER);
            try {
                conn = DriverManager.getConnection(url, USER, PASS);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return conn;
    }

    public static void closeDB(Connection conn, PreparedStatement pstm, ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        if (pstm != null) {
            try {
                pstm.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

}
