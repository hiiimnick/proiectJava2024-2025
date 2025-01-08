package clase;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class LoginController {
    @FXML
    private Label accountType;
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
        accountType.setText("Account Type: Student");
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
            accountType.setText("Account Type: Profesor");
    }

    public void switchToStudent() {
            accountType.setText("Account Type: Student");
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
        if(isProfessor){
            registerProfesor();
        } else {
            registerStudent();
        }
    }

    public void registerStudent() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/registerStudent.fxml"));
            Scene scene = new Scene(loader.load());

            RegisterStudentController controller = loader.getController();

            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error loading the application: " + e.getMessage());
        }
    }

    public void registerProfesor() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/registerProfesor.fxml"));
            Scene scene = new Scene(loader.load());

            RegisterProfessorController controller = loader.getController();

            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error loading the application: " + e.getMessage());
        }
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

    private String encodePassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(password.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

}
