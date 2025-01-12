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

    private List<Curs> loadedCourses = new ArrayList<>();
    private List<Student> loadedStudents = new ArrayList<>();
    private int professorID;

    @FXML
    private void initialize() {
        cursuriButton.setOnAction(event -> displayCourses());
        studentiButton.setOnAction(event -> displayStudents());
        noteButton.setOnAction(event -> gradeStudent());
    }

    public void setProfessorID(int id) {
        this.professorID = id;
        loadProfessorData();
        if (professorID == -1)
            System.out.println("Something isn't right, check profesori.txt and functions associated to finding/setting profesorids.");
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
            controller.setCourses("Courses you teach: " + String.join(", ", coursesName));
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
            dialog.setTitle("Select Course");
            dialog.setHeaderText("Select the course to display students:");
            dialog.setContentText("Course:");

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
            controller.setCourses("Students in " + course.getNume() + ": " + String.join(", ", studentNames));
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
            dialog.setTitle("Select Course");
            dialog.setHeaderText("Select the course to grade a student:");
            dialog.setContentText("Course:");

            dialog.showAndWait().ifPresent(this::showGradeInputForCourse);
        }
    }

    private void showGradeInputForCourse(Curs course) {
        List<Student> studentsInCourse = loadedStudents.stream()
                .filter(student -> student.getAn() == course.getAnCurs())
                .collect(Collectors.toList());

        if (studentsInCourse.isEmpty()) {
            showAlert("No Students", "No students are enrolled in this course.");
            return;
        }

        ChoiceDialog<Student> studentDialog = new ChoiceDialog<>(studentsInCourse.get(0), studentsInCourse);
        studentDialog.setTitle("Select Student");
        studentDialog.setHeaderText("Select the student to grade:");
        studentDialog.setContentText("Student:");

        studentDialog.showAndWait().ifPresent(student -> {
            TextInputDialog gradeDialog = new TextInputDialog();
            gradeDialog.setTitle("Grade Student");
            gradeDialog.setHeaderText("Enter the grade for " + student.getNumeComplet() + ":");
            gradeDialog.setContentText("Grade:");

            gradeDialog.showAndWait().ifPresent(grade -> {
                try {
                    double gradeValue = Double.parseDouble(grade);
                    if (gradeValue < 0 || gradeValue > 10) {
                        showAlert("Invalid Grade", "Grade must be between 0 and 10.");
                        return;
                    }

                    // Add the grade to the student
                    student.addGrade(course, gradeValue);
                    showAlert("Success", "Grade added successfully.");
                } catch (NumberFormatException e) {
                    showAlert("Invalid Input", "Please enter a valid number for the grade.");
                }
            });
        });
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}