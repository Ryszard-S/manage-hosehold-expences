import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import java.io.File;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class Login_controller implements Initializable {
    public ImageView imgV_lock;
    @FXML
    private Button btn_login;
    @FXML
    private Button btn_cancel;
    @FXML
    private Button btn_newAccount;
    @FXML
    private Label lbl_info;
    @FXML
    private TextField txt_login;
    @FXML
    private PasswordField txt_password;
    // Display image of lock
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        File file = new File("icons/1F512.png");
        Image img = new Image(file.toURI().toString());
        imgV_lock.setImage(img);
    }

    // Login to Data Base
    @FXML
    public void Login(ActionEvent actionEvent) {
        txt_login.setText("q");
        txt_password.setText("q");
        String user = txt_login.getText();
        String password = txt_password.getText();
        if (user != "" && password != "") {
            try {
                ResultSet result = QueryExecutor.executeSelect("SELECT * FROM login WHERE nick='" + user + "' and haslo ='" + password + "'");
                //Check if result is not empty,
                if (result.next() == false) {
                    lbl_info.setText("Błędny login lub hasło");
                } else {
                    lbl_info.setText("Logowanie poprawne");
                    User user1 = new User();
                    user1.setUserID(result.getInt("user_ID"));
                    user1.setUserName(result.getString("imie"));
                    user1.setUserNick(result.getString("nick"));

                    Stage stage = (Stage) btn_login.getScene().getWindow();
                    stage.close();

                    new MainWindow_controller().show();
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        } else lbl_info.setText("Brakuje loginu lub hasła");

    }
    // Register new account
    public void NewAccount(ActionEvent actionEvent) {
        new Registration_controller().show();
    }
    //Exit App
    public void Cancel(ActionEvent actionEvent) {
        Stage stage = (Stage) btn_cancel.getScene().getWindow();
        stage.close();
    }

}

