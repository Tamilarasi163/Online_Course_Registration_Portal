package CourseEnrollment;



import java.sql.*;

public class DB {
    private static final String URL = "jdbc:mysql://localhost:3306/course_entrollment_portal";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "tiger";
    private static Connection connection;

    public static Connection getConnection() throws SQLException {
        if (connection == null) {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        }
        return connection;
    }

    public static void closeConnection() {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static PreparedStatement prepareStatement(String sql) throws SQLException {
        return getConnection().prepareStatement(sql);
    }
}
