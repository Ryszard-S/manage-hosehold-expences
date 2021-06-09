import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.SQLException;

public class Registration_controller  {
    @FXML
    private Button btn_register;
    @FXML
    private TextField txt_imie;
    @FXML
    private TextField txt_nick;
    @FXML
    private PasswordField txt_password;
    @FXML
    private Label lbl_register;



    public void register(ActionEvent actionEvent) {
        String imie = txt_imie.getText();
        String nick = txt_nick.getText();
        String password = txt_password.getText();

        if (imie != "" && nick != "" && password != "") {

            try {
                QueryExecutor.executeQuery("INSERT INTO login (imie, nick, haslo) VALUES ('" + imie + "', '" + nick + "', '" + password + "')");
                lbl_register.setText("Użytkownik dodany do bazy danych");
            } catch (SQLException throwables) {
                throwables.printStackTrace();
                lbl_register.setText("Użytkownik o podanym nicku juz istnieje. Spróbuj ponownie.");
            }

        }else {
            lbl_register.setText("Pola niepoprawnie wypełnione");
        }
    }

    public void show(){
        FXMLLoader loader = new FXMLLoader(getClass().getResource(""));
        Parent root = null;
        try {
            root = loader.load(getClass().getResource("Registration.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Registration");
        stage.setScene(new Scene(root));
        stage.show();
    }


}
