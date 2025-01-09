package clase;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

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
        String parolaProfessor = encodePassword(this.pass.getText());

        int nextId = getNextId("src/inputData/profesori.txt");

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("src/inputData/profesori.txt", true))) {
            writer.write(nextId + "," + numeProfessor + "," + prenumeProfessor + "," + userProfessor + "," + parolaProfessor);
            writer.newLine();
            showAlert("Registration successful", "Professor registered successfully.");
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Registration failed", "Failed to register professor.");
        }
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

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}