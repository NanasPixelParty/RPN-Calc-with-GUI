package hellofx;

import java.net.URL;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class MainCalculatorWindow extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        calculatorWindow(primaryStage);
    }

    private void calculatorWindow(Stage primaryStage) throws Exception {
        try {
            URL loc = getClass().getResource("calculator-gui.fxml");
            FXMLLoader fxmlLoader = new FXMLLoader(loc);
            Pane root = fxmlLoader.load();
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.setTitle("RPC-Calculator");
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
