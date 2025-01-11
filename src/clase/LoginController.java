package clase;

import Controllers.dashboardControllers.DashboardStudentController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

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
        boolean loginSuccessful = false;

        if (isProfessor) {
            loginSuccessful = checkCredentials("src/inputData/profesori.txt", username, password);
        } else {
            loginSuccessful = checkCredentials("src/inputData/studenti.txt", username, password);
        }

        if (loginSuccessful) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Login Status");
            alert.setHeaderText(null);
            alert.setContentText("Login successful");
            alert.showAndWait();

            Stage loginStage = (Stage) login.getScene().getWindow();
            loginStage.hide();

            try {
                FXMLLoader loader;
                if (isProfessor) {
                    loader = new FXMLLoader(getClass().getResource("/FXML/dashboardProfesor.fxml"));
                } else {
                    loader = new FXMLLoader(getClass().getResource("/FXML/studentDashboard/dashboardStudent.fxml"));
                    Stage dashboardStage = new Stage();
                    dashboardStage.setScene(new Scene(loader.load()));
                    DashboardStudentController controller = loader.getController();
                    controller.setUsername(username); // Pass the username to the Dashboard controller
                    dashboardStage.show();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Login Status");
            alert.setHeaderText(null);
            alert.setContentText("Login failed");
            alert.showAndWait();
        }
    }

    public void register() {
        if (isProfessor) {
            registerProfesor();
        } else {
            registerStudent();
        }
    }

    public void registerStudent() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/registerStudent.fxml"));
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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/registerProfesor.fxml"));
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
            String hashedPassword = PasswordUtils.encodePassword(password);
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (!isProfessor && data[5].trim().equals(username) && data[6].trim().equals(hashedPassword)) {
                    return true;
                }
                else if (data[3].trim().equals(username) && data[4].trim().equals(hashedPassword)) {
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}