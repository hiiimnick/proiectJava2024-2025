import clase.NewThread;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Main  {
    public static void main(String[] args) {
        NewThread guiThread = new NewThread("GUIThread", "GUI");
        NewThread consoleThread = new NewThread("ConsoleThread", "Console");

        guiThread.start();
        consoleThread.start();
    }
}