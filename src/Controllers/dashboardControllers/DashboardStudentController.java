package Controllers.dashboardControllers;

import Controllers.DisplayController;

import clase.FileDataManager;
import clase.Curs;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class DashboardStudentController {

    @FXML
    private TextField anUniversitar;

    @FXML
    private Button loadButton;

    @FXML
    private Button cursuriButton;

    @FXML
    private Button noteButton;

    @FXML
    private Button medieButton;

    @FXML
    private Button restanteButton;

    @FXML
    private Button logoutButton;

    private List<Curs> loadedCourses;
    private StringBuilder loadedGrades;
    private String username;
    private int studentId;

    @FXML
    private void initialize() {
        cursuriButton.setVisible(false);
        noteButton.setVisible(false);
        medieButton.setVisible(false);
        restanteButton.setVisible(false);

        loadButton.setOnAction(event -> loadStudentData());
        cursuriButton.setOnAction(event -> displayCourses());
        noteButton.setOnAction(event -> displayGrades());
        medieButton.setOnAction(event -> displayAverageGrade());
        restanteButton.setOnAction(event -> displayFailedCourses());
        logoutButton.setOnAction(event -> logout());
    }

    public void setUsername(String username) {
        this.username = username; // Add this method
        this.studentId = getStudentIdByUsername(username);// Get the student ID based on the username
        if (studentId == -1)
            System.out.println("Ceva nu este in regula, verificati studenti.txt si functiile asociate gasirii/setarii id-ului studentului.");
    }

    private int getStudentIdByUsername(String username) {
        try (BufferedReader reader = new BufferedReader(new FileReader("src/inputData/studenti.txt"))) {
            String line;
            reader.readLine(); // Skip header
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts[5].equals(username)) {
                    int studentId = Integer.parseInt(parts[0]);
                    //System.out.println("Found student ID: " + studentId);
                    return studentId;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return -1; // Return -1 if the student ID is not found
    }

    private void loadStudentData() {
        String anSelectat = anUniversitar.getText();
        if (anSelectat.isEmpty() || Integer.parseInt(anSelectat) > 4) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("An invalid");
            alert.setHeaderText(null);
            alert.setContentText("Anul universitar selectat nu este valid (1-4).");
            alert.showAndWait();
            return;
        }

        FileDataManager fileDataManager = FileDataManager.getInstance();
        loadedCourses = fileDataManager.createCoursesData().stream()
                .filter(curs -> Integer.toString(curs.getAnCurs()).equals(anSelectat))
                .collect(Collectors.toList());

        loadedGrades = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader("src/inputData/note.txt"))) {
            String line;
            reader.readLine(); // Skip header
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                int courseId = Integer.parseInt(parts[0]);
                int studentIdFromFile = Integer.parseInt(parts[1]);
                if (studentIdFromFile == studentId && loadedCourses.stream().anyMatch(curs -> curs.getID() == courseId)) {
                    String courseName = loadedCourses.stream()
                            .filter(curs -> curs.getID() == courseId)
                            .map(Curs::getNume)
                            .findFirst()
                            .orElse("Curs necunoscut");
                    loadedGrades.append("Curs: ").append(courseName)
                            .append(", Nota: ").append(parts[2])
                            .append("\n");
                    //System.out.println("Course: " + courseName + ", Grade: " + parts[2]);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        cursuriButton.setVisible(true);
        noteButton.setVisible(true);
        medieButton.setVisible(true);
        restanteButton.setVisible(true);
    }

    private void displayCourses() {
        List<String> coursesName = loadedCourses.stream()
                .map(Curs::getNume)
                .collect(Collectors.toList());
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/studentDashboard/display.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(loader.load()));
            stage.setTitle("Cursuri");
            DisplayController controller = loader.getController();
            controller.setCourses("Cursurile tale: " + String.join(", ", coursesName));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void displayGrades() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/studentDashboard/display.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(loader.load()));
            stage.setTitle("Note");
            DisplayController controller = loader.getController();
            controller.setCourses(loadedGrades.toString());
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void displayAverageGrade() {
        String[] gradesLines = loadedGrades.toString().split("\n");
        double totalGrades = 0;
        int count = 0;

        for (String line : gradesLines) {
            String[] parts = line.split(", Nota: ");
            if (parts.length == 2) {
                double grade = Double.parseDouble(parts[1]);
                if (grade != 0 && grade > 4) {
                    totalGrades += grade;
                    count++;
                }
            }
        }

        double averageGrade = (count > 0) ? totalGrades / count : 0;
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Media ta");
        alert.setHeaderText(null);
        alert.setContentText("Media ta este: " + averageGrade);
        alert.showAndWait();
    }

    private void displayFailedCourses() {
        String[] gradesLines = loadedGrades.toString().split("\n");
        StringBuilder failedCourses = new StringBuilder();

        for (String line : gradesLines) {
            String[] parts = line.split(", Nota: ");
            if (parts.length == 2) {
                double grade = Double.parseDouble(parts[1]);
                if (grade <= 4) {
                    String courseName = parts[0].replace("Curs: ", "");
                    failedCourses.append(courseName).append("\n");
                }
            }
        }

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Restante");
        alert.setHeaderText(null);
        alert.setContentText(failedCourses.length() > 0 ? failedCourses.toString() : "Fara restante.");
        alert.showAndWait();
    }

    private void logout() {
        Stage stage = (Stage) logoutButton.getScene().getWindow();
        stage.close();
        showLoginScreen();
    }

    private void showLoginScreen() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/login.fxml"));
            Stage loginStage = new Stage();
            loginStage.setScene(new Scene(loader.load()));
            loginStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
