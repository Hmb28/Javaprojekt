package de.hmb.pp.test;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainView {

    public static final int WIDTH = 150;

    private Stage currentStage;

    /**
     * Handles the click event for the Schere-Stein-Papier button.
     * @throws IOException if the fxml file cannot be loaded
     */
    @FXML
    protected void onSSPClick() throws IOException {
        //open SchereSteinPapie.fxml
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("schere-stein-papier.fxml"));
        Parent root = fxmlLoader.load();
        if(currentStage != null) {
            currentStage.close();
        }
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle("Schere-Stein-Papier");
        stage.show();
        currentStage = stage;
    }

    /**
     * Handles the click event for the Tic-Tac-Toe button.
     * @throws IOException if the fxml file cannot be loaded
     */
    @FXML
    protected void onTTTClick() throws IOException {
        //open TicTacToe.fxml
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("tic-tac-toe.fxml"));
        Parent root = fxmlLoader.load();

        if(currentStage != null) {
            currentStage.close();
        }

        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle("Tic-Tac-Toe");
        stage.show();
    }

    /**
     * Handles the click event for the BlackJack button.
     * @throws IOException if the fxml file cannot be loaded
     */
    @FXML
    protected void onBJClick() throws IOException {
        //open BlackJack.fxml
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("black-jack.fxml"));
        Parent root = fxmlLoader.load();

        if(currentStage != null) {
            currentStage.close();
        }

        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setHeight(600);
        stage.setWidth(800);
        stage.setTitle("BlackJack");
        stage.show();
    }
}
