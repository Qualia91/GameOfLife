package sample;

import javafx.collections.ObservableArray;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Box;
import javafx.scene.shape.Rectangle;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.*;

public class Controller implements Initializable {

    private final MainModel mainModel;



    @FXML
    public Pane pane;

    public Controller(MainModel mainModel) {

        this.mainModel = mainModel;

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        mainModel.getExecutor().scheduleAtFixedRate(
                () -> {
                    for (int rowIndex = 0; rowIndex < mainModel.getCells().length; rowIndex++) {
                        for (int colIndex = 0; colIndex < mainModel.getCells()[rowIndex].length; colIndex++) {
                            if (mainModel.getCells()[rowIndex][colIndex] == 1) {
                                pane.getChildren().add(new Rectangle(rowIndex, colIndex, 1.0, 1.0));
                            }
                        }
                    }
                }, 0, 1, TimeUnit.SECONDS);


    }

}
