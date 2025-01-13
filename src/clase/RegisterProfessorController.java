package clase;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.io.*;

public class RegisterProfessorController {
    @FXML
    private TextField nume;
    @FXML
    private TextField prenume;
    @FXML
    private TextField user;
    @FXML
    private TextField pass;
    @FXML
    private Button registerProfesor;

    public void initialize() {
        registerProfesor.setOnAction(e -> registerProfessor());
    }

    private void registerProfessor() {
        String numeProfessor = this.nume.getText();
        String prenumeProfessor = this.prenume.getText();
        String userProfessor = this.user.getText();
        String parolaProfessor = PasswordUtils.encodePassword(this.pass.getText());

        if (emptyField()) {
            showAlert("Inregistrarea a esuat!", "Te rog sa completezi toate campurile.");
            return;
        }

        if (!isProfessorInfoUnique(userProfessor)) {
            showAlert("Inregistrarea a esuat!", "Utilizatorul exista deja.");
            return;
        }

        int nextId = getNextId("src/inputData/profesori.txt");

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("src/inputData/profesori.txt", true))) {
            writer.write(nextId + "," + numeProfessor + "," + prenumeProfessor + "," + userProfessor + "," + parolaProfessor);
            writer.newLine();
            showAlert("Inregistrare reusita!", "Profesor inregistrat cu succes.");
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Inregistrare esuata!", "Profesorul nu a putut fi inregistrat.");
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
        return nume.getText().isEmpty() ||
                prenume.getText().isEmpty() ||
                user.getText().isEmpty() ||
                pass.getText().isEmpty() ||
                !nume.getText().matches("^[a-zA-Z]*$") ||
                !prenume.getText().matches("^[a-zA-Z]*$");
    }

    private boolean isProfessorInfoUnique(String user) {
        try (BufferedReader reader = new BufferedReader(new FileReader("src/inputData/profesori.txt"))) {
            String line;
            reader.readLine(); // Skip header
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts[3].equalsIgnoreCase(user)) {
                    return false;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}