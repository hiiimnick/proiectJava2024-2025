import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class Main extends Application {
    public static void main(String[] args){
        launch();
    }

    @Override
    public void start(Stage stage) throws Exception {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/login.fxml"));
            Scene scene = new Scene(loader.load());
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Eroare la incarcarea aplicatiei: " + e.getMessage());
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
    //rezolvat, dadea crash din cauza
    //ca nu exista login.fxml
}