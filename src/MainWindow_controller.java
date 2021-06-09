import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URL;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class MainWindow_controller implements Initializable {

    @FXML
    private TableView<Transaction> table;

    @FXML
    private TableColumn<Transaction, Integer> col_tranzakcja_ID;

    @FXML
    private TableColumn<Transaction, String> col_data;

    @FXML
    private TableColumn<Transaction, Double> col_kwota;

    @FXML
    private TableColumn<Transaction, String> col_kategoria;
    @FXML
    private DatePicker txt_data;

    @FXML
    private TextField txt_kwota;

    @FXML
    private ChoiceBox<Category> cBox_kategoria;

    @FXML
    private BarChart<?, ?> barchart;

    @FXML
    private DatePicker date_from;

    @FXML
    private DatePicker date_to;

    @FXML
    private Button btn_tableDate;


    @FXML
    private Label lbl_welcome;

    @FXML
    private TableView<Category> table_Category;

    @FXML
    private TableColumn<Category, String> col_kategoria1;

    @FXML
    private Button btn_add1;

    @FXML
    private Button btn_delete1;

    @FXML
    private Label lbl_welcome1;

    @FXML
    private TextField txt_Category;




    ObservableList<Transaction> list = FXCollections.observableArrayList();
    ObservableList<Category> category_list = FXCollections.observableArrayList();

    Date dateFrom;
    Date dateTo;

    LocalDate dateFrom1;
    LocalDate dateTo1;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        try {
            ResultSet result = QueryExecutor.executeSelect("SELECT MIN(data), MAX(data) FROM tranzakcje WHERE user_ID='"+User.getUserID()+"'");
            result.next();
            dateFrom=result.getDate(1);
            dateTo=result.getDate(2);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        try {
            ResultSet result = QueryExecutor.executeSelect("SELECT * FROM tranzakcje WHERE user_ID='" + User.getUserID() + "'");
            while (result.next()) {
                list.add(new Transaction(result.getInt("tranzakcja_ID"), result.getString("data"), result.getDouble("kwota"), result.getString("kategoria")));
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        for (String x : CategoryList.CategoryList()) {
            category_list.add(new Category(x));
        }



        table_update();
        table_update_category();

        cBox_kategoria.setItems(category_list);

        LocalDate ld = LocalDate.now();
        txt_data.setValue(ld);

        date_from.setValue(dateFrom.toLocalDate());
        date_to.setValue(dateTo.toLocalDate());
        lbl_welcome.setText("Witaj, "+User.getUserName());
    }

    public void show() {

        FXMLLoader loader = new FXMLLoader(getClass().getResource(""));
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("MainWindow.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Stage stage = new Stage();
        stage.setTitle("Expenses");
        Scene scene = new Scene(root);
        scene.getStylesheets().add("MainWindow.css");
        scene.setFill(Color.TRANSPARENT);
        stage.setScene(scene);
        stage.show();
    }


    Double sum_category(String nameCategory) throws SQLException {
        dateFrom1 = date_from.getValue();
        dateTo1 = date_to.getValue();
        Double sum = 0.0;
        ResultSet resultSet =  QueryExecutor.executeSelect("SELECT sum(kwota) FROM tranzakcje WHERE user_ID ='"+User.getUserID()+"' AND kategoria = '"+nameCategory+"' AND data BETWEEN \""+dateFrom1+"\" AND \""+dateTo1+"\"");
        while (resultSet.next()){
            sum = resultSet.getDouble(1);
        }

        return sum;
    }

    private void table_update(){
        col_tranzakcja_ID.setCellValueFactory(new PropertyValueFactory<>("tranzakcja_Id"));
        col_data.setCellValueFactory(new PropertyValueFactory<>("Data"));
        col_kwota.setCellValueFactory(new PropertyValueFactory<>("kwota"));
        col_kategoria.setCellValueFactory(new PropertyValueFactory<>("kategoria"));
        table.setItems(list);
    }

    private  void table_update_category(){
        col_kategoria1.setCellValueFactory(new PropertyValueFactory<>("categoryName"));
        table_Category.setItems(category_list);
    }

    @FXML
    public void Add(ActionEvent actionEvent) {
        String data =  txt_data.getValue().toString();
        BigDecimal kwota = null;
        Double doubleKwota = null;
        try {
            doubleKwota= Double.parseDouble(txt_kwota.getText());
            txt_kwota.setStyle("-fx-background-color: #FFFFFF");
            kwota = new BigDecimal(doubleKwota).setScale(2, RoundingMode.DOWN);
            System.out.println(kwota);

            Category kategoria = cBox_kategoria.getValue();

            if (data != null && kwota != BigDecimal.ZERO && kategoria != null) {
                System.out.println(data);
                try {
                    QueryExecutor.executeQuery("INSERT INTO tranzakcje (data, kwota, user_ID, kategoria) VALUES ('" + data + "', '" + kwota + "', '" + User.getUserID() + "', '" + kategoria + "')");
                    System.out.println("Wykonany insert");
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
                try {
                    System.out.println("Wykonany select 1 poz.");
                    ResultSet result = QueryExecutor.executeSelect("SELECT * FROM tranzakcje WHERE user_ID ='"+User.getUserID()+"' ORDER BY  tranzakcja_ID DESC LIMIT 1");
                    while (result.next()) {
                        list.add(new Transaction(result.getInt("tranzakcja_ID"), result.getString("data"), result.getDouble("kwota"), result.getString("kategoria")));
                    }
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
                table_update();
            }
        }
        catch (NumberFormatException e){
            System.out.println("Błędna liczba");
            txt_kwota.setStyle("-fx-background-color: #ff7248");
        }
    }

    @FXML
    void delete(ActionEvent event) {
        int index = table.getSelectionModel().selectedIndexProperty().get();
        int i = table.getSelectionModel().getSelectedItem().getTranzakcja_Id();
        try {
            QueryExecutor.executeQuery("DELETE FROM tranzakcje WHERE tranzakcja_ID = '"+i+"'");
            //lbl_register.setText("Użytkownik dodany do bazy danych");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            //lbl_register.setText("Użytkownik o podanym nicku juz istnieje. Spróbuj ponownie.");
        }
        list.remove(index);
        table_update();
    }


    // Add new category to SQL based
    @FXML
    void newCategory(ActionEvent event){
// SQL question INSERT INTO kategorie (kategoria) VALUES ('nowa')
    }
    @FXML
    void btn_add1(ActionEvent e){
        String newCategory = txt_Category.getText();
        if (newCategory != null)
            try {
            QueryExecutor.executeQuery("INSERT INTO kategorie (kategoria) VALUES ('"+newCategory+"')");
            category_list.add(new Category(newCategory));
            table_update_category();
             } catch (SQLException throwables) {
            throwables.printStackTrace();
             }

        else{
            lbl_welcome1.setText("Brak kategorii");
        }
    }

    @FXML
    void chart() throws SQLException {
        barchart.getData().clear();
        XYChart.Series dataSeries1 = new XYChart.Series();

        for(Object x : CategoryList.CategoryList()){
            dataSeries1.getData().add(new XYChart.Data(x, sum_category((String) x)));
        }
        barchart.getData().add(dataSeries1);

        // sql question: SELECT sum(kwota) FROM `tranzakcje` WHERE user_ID =2 AND kategoria = 'papierosy'
    }

    @FXML
    void table_date(ActionEvent event){
        list.clear();
        dateFrom1=date_from.getValue();
        dateTo1=date_to.getValue();
        try {
            ResultSet result = QueryExecutor.executeSelect("SELECT * FROM tranzakcje WHERE user_ID='" + User.getUserID() + "' AND  data BETWEEN \""+dateFrom1+"\" AND \""+dateTo1+"\"");

            while (result.next()) {
                list.add(new Transaction(result.getInt("tranzakcja_ID"), result.getString("data"), result.getDouble("kwota"), result.getString("kategoria")));
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        table_update();
    }





    // sql wybieranie dat SELECT * FROM `tranzakcje` WHERE `data` BETWEEN "2021-06-01" AND "2021-06-06"
}
