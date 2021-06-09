import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnector {
    private static String URL = "jdbc:mariadb://localhost:3306/DB";
    private static String USER = "root";
    private static String PASSWORD = "";
    public static Connection connect(){
        Connection connection = null;
        try {
            connection =  DriverManager.getConnection("jdbc:mariadb://localhost:3306/wydatki?user=root&password=");
//            connection=DriverManager.getConnection(URL,USER,PASSWORD);
            System.out.println("połączenie udane");

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return connection;
    }
}
