package clase;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.io.*;

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

        if(emptyField()) {
            showAlert("Inregistrarea a esuat!", "Te rog sa completezi toate campurile.");
            return;
        }

        int nextId = getNextId("src/inputData/studenti.txt");

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("src/inputData/studenti.txt", true))) {
            writer.write(nextId + "," + numeStudent + "," + prenumeStudent + "," + grupaStudent + "," + anStudent + "," + userStudent + "," + parolaStudent);
            writer.newLine();
            showAlert("Inregistrare reusita!", "Student inregistrat cu succes.");
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

    private boolean emptyField() {
        return nume.getText().isEmpty() || prenume.getText().isEmpty() || grupa.getText().isEmpty() || an.getText().isEmpty() || user.getText().isEmpty() || pass.getText().isEmpty();
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}