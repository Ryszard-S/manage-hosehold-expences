import java.sql.*;

public class QueryExecutor {
    public static ResultSet executeSelect(String selectQuery) throws SQLException {
            Connection connection = DbConnector.connect();
            Statement statement = connection.createStatement();
            return statement.executeQuery(selectQuery);
    }

    public static void executeQuery (String query) throws SQLException {
            Connection connection = DbConnector.connect();
            Statement statement = connection.createStatement();
            statement.execute(query);
    }
}
