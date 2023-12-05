package connection;

import java.sql.*;

public class ConnectionFactory {
    private static final String DRIVER = "oracle.jdbc.OracleDriver";
    private static final String URL = "jdbc:oracle:thin:@//localhost:1521/xe";
    private static final String USER = "system";
    private static final String PASS = "1234";

    public static Connection getConnection() {
        try {
            Class.forName(DRIVER);
            return DriverManager.getConnection(URL, USER, PASS);
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException("Erro na conexão: " + e);
        }
    }

    public static void closeConnection(Connection conn) {
        try {
            if(conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void closeConnection(Connection conn, PreparedStatement stmt) {
        closeConnection(conn);

        try {
            if(stmt != null) {
                stmt.close();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void closeConnection(Connection conn, PreparedStatement stmt, ResultSet rs) {
        closeConnection(conn, stmt);

        try {
            if(rs != null) {
                rs.close();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
