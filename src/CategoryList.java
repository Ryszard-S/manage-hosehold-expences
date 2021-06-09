import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CategoryList {
    public static ArrayList  CategoryList() {
        ArrayList<String> kat = new ArrayList<>();
        try {
            ResultSet result1 = null;
            result1 = QueryExecutor.executeSelect("SELECT * FROM kategorie");

            while (result1.next()) {
                kat.add(result1.getString("kategoria"));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return kat;
    }
}
