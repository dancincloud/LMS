package DBUtil;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

/**
 * @author : Ethan Zhang
 * @description :
 * @createTime : [2021/4/25 0:04]
 * @updateTime : [2021/4/25 0:04]
 */
public class DBConnection {

    private static Properties prop;
    static{
        try {
            prop = new Properties();
            prop.load(new FileInputStream("./Final Project/resources/database.properties"));
        } catch (FileNotFoundException e) {
            System.out.println("database.properties not found!");
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static final String JDBC_DRIVER = prop.getProperty("JDBC_DRIVER");
    static final String DEFAULT_DB_URL = prop.getProperty("DEFAULT_DB_URL");
    static final String DB_URL = prop.getProperty("DB_URL");

    // user and password
    static final String USER = prop.getProperty("USER");
    static final String PASS = prop.getProperty("PASS");

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


    public static void main(String[] args) {
        Connection coon = getConnection(DB_URL);

        System.out.println(JDBC_DRIVER);
        System.out.println(DEFAULT_DB_URL);
        System.out.println(DB_URL);
        System.out.println(USER);
        System.out.println(PASS);

        closeDB(coon ,null, null);
    }
}
