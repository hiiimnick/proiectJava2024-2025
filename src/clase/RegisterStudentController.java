package clase;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.io.*;
import java.util.List;

public class RegisterStudentController {
    @FXML
    private TextField nume;
    @FXML
    private TextField prenume;
    @FXML
    private TextField grupa;
    @FXML
    private TextField an;
    @FXML
    private TextField user;
    @FXML
    private TextField pass;
    @FXML
    private Button registerStudent;

    public void initialize() {
        registerStudent.setOnAction(e -> registerStudent());
    }

    private void registerStudent() {
        String numeStudent = this.nume.getText();
        String prenumeStudent = this.prenume.getText();
        String grupaStudent = this.grupa.getText();
        String anStudent = this.an.getText();
        String userStudent = this.user.getText();
        String parolaStudent = PasswordUtils.encodePassword(this.pass.getText());

        if(verifyInfoRegister()) {
            showAlert("Inregistrarea a esuat!", "Te rog sa completezi toate campurile corespunzator. (Grupa si An trebuie sa contina doar cifre, numele si prenumele sa nu contina cifre)");
            return;
        }

        if (!isStudentInfoUnique(userStudent)) {
            showAlert("Inregistrarea a esuat!", "Utilizatorul exista deja.");
            return;
        }

        int nextId = getNextId("src/inputData/studenti.txt");

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("src/inputData/studenti.txt", true))) {
            writer.write(nextId + "," + numeStudent + "," + prenumeStudent + "," + grupaStudent + "," + anStudent + "," + userStudent + "," + parolaStudent);
            writer.newLine();
            showAlert("Inregistrare reusita!", "Student inregistrat cu succes.");
            addNullGradesForStudentCourses(nextId, Integer.parseInt(anStudent));
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Inregistrarea a esuat!", "Studentul nu a putut fi inregistrat.");
        }

    }

    private int getNextId(String filePath) {
        int maxId = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            reader.readLine();
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                int id = Integer.parseInt(parts[0]);
                if (id > maxId) {
                    maxId = id;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return maxId + 1;
    }

    private boolean verifyInfoRegister() {
        return nume.getText().isEmpty() ||
                prenume.getText().isEmpty() ||
                grupa.getText().isEmpty() ||
                an.getText().isEmpty() ||
                user.getText().isEmpty() ||
                pass.getText().isEmpty() ||
                !nume.getText().matches("^[a-zA-Z]*$") ||
                !prenume.getText().matches("^[a-zA-Z]*$") ||
                !grupa.getText().matches("\\d+") ||
                Integer.parseInt(an.getText()) < 1 ||
                Integer.parseInt(an.getText()) > 4 ||
                !an.getText().matches("\\d+");
        // "\\d+" verifica daca stringul contine doar cifre(\d inseamna o cifra iar + inseamna ca se repeta de 1 sau mai multe ori)
        // "^[a-zA-Z]*$" verifica daca stringul contine doar litere de la a la z sau de la A la Z
        // ^ verifica inceputul stringului iar $ verifica sfarsitul stringului
    }

    private boolean isStudentInfoUnique(String user) {
        try (BufferedReader reader = new BufferedReader(new FileReader("src/inputData/studenti.txt"))) {
            String line;
            reader.readLine(); // Skip header
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts[5].equalsIgnoreCase(user)) {
                    return false;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    private void addNullGradesForStudentCourses(int studentId, int studentYear) {
        FileDataManager fileDataManager = FileDataManager.getInstance();
        List<Curs> courses = fileDataManager.createCoursesData();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("src/inputData/note.txt", true))) {
            for (Curs course : courses) {
                if (course.getAnCurs() == studentYear) {
                    writer.write(course.getID() + "," + studentId + ",0");
                    writer.newLine();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}