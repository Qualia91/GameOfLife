package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class Main extends Application {

    private MainModel mainModel;
    ScheduledExecutorService executor;

    @Override
    public void start(Stage primaryStage) {

        try {
            int width = 800;
            int height = 600;

            executor = Executors.newScheduledThreadPool(2);

            MainModel mainModel = new MainModel(width, height, executor);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("sample.fxml"));

            loader.setControllerFactory(controller -> {
                if (controller.equals(Controller.class)) {
                    return new Controller(mainModel);
                } else {
                    throw new RuntimeException("Failed to create controller");
                }
            });

            Parent root = loader.load();

            primaryStage.setTitle("RTS");
            primaryStage.setScene(new Scene(root, width, height));
            primaryStage.show();

            primaryStage.setOnCloseRequest((stage) -> closeThreads());

        } catch (Exception e) {
            e.printStackTrace();
            closeThreads();
        }
    }

    private void closeThreads() {
        System.out.println("Closing");
        if (executor != null) {
            if (!executor.isShutdown()) {
                executor.shutdownNow();
            }
        }

    }

    public static void main(String[] args) {
        launch(args);
    }
}
