package Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class DisplayController {
    @FXML
    private Label displayLabel;
    @FXML
    private Button backButton;

    public void initialize() {
        backButton.setOnAction(event -> displayLabel.getScene().getWindow().hide());
    }

    public void setCourses(String courses) {
        displayLabel.setText(courses);
    }
}