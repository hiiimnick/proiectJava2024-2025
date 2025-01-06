package clase;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;


public class LoginController {
    @FXML
    private Label label;
    @FXML
    private TextField user;
    @FXML
    private PasswordField pass;
    @FXML
    private Button schimbare; //switch la mod profesor
    @FXML
    private Button login;
    @FXML
    private Button register;

    public void initialize() {
        label.setText("Login");
    }

    public void switchToProfesor() {
        schimbare.setOnAction(e -> {
            label.setText("Profesor");
        });
    }

    public void switchToStudent() {
        schimbare.setOnAction(e -> {
            label.setText("Student");
        });
    }

    public void login() {
        login.setOnAction(e -> {
            String username = user.getText();
            String password = pass.getText();
            //momentan hardcoded sa fie admin admin
            //o sa schimb la credentialele din fisier mai tarziu
            //+ detectie daca e student sau profesor
            //+ switch la fereastra corespunzatoare
            if (username.equals("admin") && password.equals("admin")) {
                label.setText("Login succesful");
            } else {
                label.setText("Login failed");
            }
        });
    }

    public void register() {
        register.setOnAction(e -> {
            label.setText("Register");
        });
    }
}
