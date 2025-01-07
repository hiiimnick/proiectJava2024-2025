package clase;

import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;


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

    private boolean isProfessor = false;

    public void initialize() {
        label.setText("Student");
        schimbare.setOnAction(e -> switchMode());
        login.setOnAction(e -> login());
        register.setOnAction(e -> register());
    }

    public void switchMode() {
        if (isProfessor) {
            switchToStudent();
        } else {
            switchToProfesor();
        }
        isProfessor = !isProfessor;
    }

    public void switchToProfesor() {
            label.setText("Profesor");
    }

    public void switchToStudent() {
            label.setText("Student");
    }

    public void login() {
            String username = user.getText();
            String password = pass.getText();
            //momentan hardcoded sa fie admin admin
            //o sa schimb la credentialele din fisier mai tarziu
            //+ detectie daca e student sau profesor
            //+ switch la fereastra corespunzatoare
            if (username.equals("admin") && password.equals("admin")) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Login Status");
                alert.setHeaderText(null);
                alert.setContentText("Login successful");
                alert.showAndWait();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Login Status");
                alert.setHeaderText(null);
                alert.setContentText("Login failed");
                alert.showAndWait();
            }
            /* cum o sa arate codul dupa ce termin partea de password encoding
        public void login() {
            String username = user.getText();
            String password = pass.getText();
            boolean loginSuccessful = false;

            if (isProfessor) {
                loginSuccessful = checkCredentials("inputData/profesori.txt", username, password);
            } else {
                loginSuccessful = checkCredentials("inputData/studenti.txt", username, password);
            }

            if (loginSuccessful) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Login Status");
                alert.setHeaderText(null);
                alert.setContentText("Login successful");
                alert.showAndWait();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Login Status");
                alert.setHeaderText(null);
                alert.setContentText("Login failed");
                alert.showAndWait();
            }
        }
            */
    }

    public void register() {
            label.setText("Register");
    }

    private boolean checkCredentials(String filePath, String username, String password) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data[1].trim().equals(username) && data[2].trim().equals(password)) {
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}
