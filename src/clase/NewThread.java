package clase;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class NewThread extends Thread {
    private String exeName;
    private String threadType;

    public NewThread(String name, String threadType) {
        super(name);
        this.exeName = name;
        this.threadType = threadType;
        //System.out.println("New thread: " + this);
    }

    @Override
    public void run() {
        if ("GUI".equalsIgnoreCase(threadType)) {
            Application.launch(GUIApplication.class);
        } else if ("Console".equalsIgnoreCase(threadType)) {
            runConsoleThread();
        }
    }

    private void runConsoleThread() {
        LoginControllerConsole loginControllerConsole = new LoginControllerConsole();

        loginControllerConsole.start();
    }

    public static class GUIApplication extends Application {
        @Override
        public void start(Stage stage) throws Exception {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/login.fxml"));
                Scene scene = new Scene(loader.load());
                stage.setScene(scene);
                stage.setTitle("Login");
                stage.show();
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Eroare in incarcarea paginii de start: " + e.getMessage());
            }
        }
    }
}