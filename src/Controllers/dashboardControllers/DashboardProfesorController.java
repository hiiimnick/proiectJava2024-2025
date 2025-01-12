package Controllers.dashboardControllers;

import Controllers.DisplayController;
import clase.Curs;
import clase.FileDataManager;
import clase.Student;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DashboardProfesorController {

    @FXML
    private Button cursuriButton;

    @FXML
    private Button studentiButton;

    @FXML
    private Button noteButton;

    @FXML
    private Button logoutButton;

    private List<Curs> loadedCourses = new ArrayList<>();
    private List<Student> loadedStudents = new ArrayList<>();
    private int professorID;

    @FXML
    private void initialize() {
        cursuriButton.setOnAction(event -> displayCourses());
        studentiButton.setOnAction(event -> displayStudents());
        noteButton.setOnAction(event -> gradeStudent());
        logoutButton.setOnAction(event -> logout());
    }

    public void setProfessorID(int id) {
        this.professorID = id;
        loadProfessorData();
        if (professorID == -1)
            System.out.println("Ceva nu este in regula, verifica profesori.txt si functiile asociate gasirii/setarii id-urile profesorilor.");
    }

    private void loadProfessorData() {
        FileDataManager fileDataManager = new FileDataManager();
        loadedCourses = fileDataManager.createCoursesData().stream()
                .filter(curs -> curs.getIDProfesor() == professorID)
                .collect(Collectors.toList());
        loadedStudents = fileDataManager.createStudentsData();
    }

    private void displayCourses() {
        List<String> coursesName = loadedCourses.stream()
                .map(Curs::getNume)
                .collect(Collectors.toList());
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/profesorDashboard/display.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(loader.load()));
            DisplayController controller = loader.getController();
            controller.setCourses("Cursurile pe care le predai: " + String.join(", ", coursesName));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void displayStudents() {
        if (loadedCourses.size() == 1) {
            showStudentsForCourse(loadedCourses.get(0));
        } else {
            // Show a dialog to select a course
            ChoiceDialog<Curs> dialog = new ChoiceDialog<>(loadedCourses.get(0), loadedCourses);
            dialog.setTitle("Selecteaza cursul");
            dialog.setHeaderText("Selecteaza cursul pentru a vedea studentii:");
            dialog.setContentText("Curs:");

            dialog.showAndWait().ifPresent(this::showStudentsForCourse);
        }
    }

    private void showStudentsForCourse(Curs course) {
        List<String> studentNames = loadedStudents.stream()
                .filter(student -> student.getAn() == course.getAnCurs())
                .map(Student::getNumeComplet)
                .collect(Collectors.toList());
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/profesorDashboard/display.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(loader.load()));
            DisplayController controller = loader.getController();
            controller.setCourses("Studenti in: " + course.getNume() + ": " + String.join(", ", studentNames));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void gradeStudent() {
        if (loadedCourses.size() == 1) {
            showGradeInputForCourse(loadedCourses.get(0));
        } else {
            // Show a dialog to select a course
            ChoiceDialog<Curs> dialog = new ChoiceDialog<>(loadedCourses.get(0), loadedCourses);
            dialog.setTitle("Selecteaza cursul");
            dialog.setHeaderText("Selecteaza cursul pentru a adauga note:");
            dialog.setContentText("Curs:");

            dialog.showAndWait().ifPresent(this::showGradeInputForCourse);
        }
    }

    private void showGradeInputForCourse(Curs course) {
        List<Student> studentsInCourse = loadedStudents.stream()
                .filter(student -> student.getAn() == course.getAnCurs())
                .collect(Collectors.toList());

        if (studentsInCourse.isEmpty()) {
            showAlert("Nu sunt studenti", "Nu sunt studenti inscrisi la acest curs.");
            return;
        }

        ChoiceDialog<Student> studentDialog = new ChoiceDialog<>(studentsInCourse.get(0), studentsInCourse);
        studentDialog.setTitle("Selecteaza studentul");
        studentDialog.setHeaderText("Selecteaza studentul pentru a adauga nota:");
        studentDialog.setContentText("Student:");

        studentDialog.showAndWait().ifPresent(student -> {
            TextInputDialog gradeDialog = new TextInputDialog();
            gradeDialog.setTitle("Noteaza studentul");
            gradeDialog.setHeaderText("Introdu nota pentru " + student.getNumeComplet() + ":");
            gradeDialog.setContentText("Nota:");

            gradeDialog.showAndWait().ifPresent(grade -> {
                try {
                    double gradeValue = Double.parseDouble(grade);
                    if (gradeValue < 0 || gradeValue > 10) {
                        showAlert("Nota invalida", "Nota trebuie sa fie intre 0 si 10.");
                        return;
                    }

                    // Add the grade to the student
                    student.addGrade(course, gradeValue);
                    showAlert("Success", "Nota a fost adaugata cu succes.");
                } catch (NumberFormatException e) {
                    showAlert("Nota invalida", "Nota trebuie sa fie un numar.");
                }
            });
        });
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

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}