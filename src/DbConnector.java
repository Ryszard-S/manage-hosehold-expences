import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnector {

    private static String DataBaseName="wydatki";
    private static String URL = "jdbc:mariadb://localhost:3306/"+DataBaseName;
    private static String USER = "root";
    private static String PASSWORD = "";
    public static Connection connect(){
        Connection connection = null;
        try {
          //  connection =  DriverManager.getConnection("jdbc:mariadb://localhost:3306/wydatki?user=root&password=");
            connection=DriverManager.getConnection(URL,USER,PASSWORD);


        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return connection;
    }
}
