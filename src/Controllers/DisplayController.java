package Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class DisplayController {
    @FXML
    private Label displayLabel;

    public void setCourses(String courses) {
        displayLabel.setText(courses);
    }
}