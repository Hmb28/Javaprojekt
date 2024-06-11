package de.hmb.pp.test.ssp;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class SchereSteinPapier {

    @FXML
    private Label computerChoiceLabel;

    @FXML
    private Label playerChoiceLabel;

    @FXML
    private Button rockButton;

    @FXML
    private Label resultLabel;

    @FXML
    private Button paperButton;

    @FXML
    private Button scissorsButton;

    private String playerChoice;
    private final String[] choices = {"Schere", "Stein", "Papier"};
    private Timer countdownTimer;
    private int countdownValue;

    @FXML
    protected void onSchereClick() {
        System.out.println("Schere");
        playerChoice = "Schere";
        handleReveal();
    }

    @FXML
    protected void onSteinClick() {
        System.out.println("Stein");
        playerChoice = "Stein";
        handleReveal();
    }

    @FXML
    protected void onPapierClick() {
        System.out.println("Papier");
        playerChoice = "Papier";
        handleReveal();
    }

    private void handleReveal() {
        countdownValue = 3;
        rockButton.setDisable(true);
        paperButton.setDisable(true);
        scissorsButton.setDisable(true);
        Platform.runLater(() -> resultLabel.setText("Computer wählt..."));
        Platform.runLater(() -> playerChoiceLabel.setText("Spieler: " + playerChoice));
        Platform.runLater(() -> computerChoiceLabel.setText("Computer: " + String.valueOf(countdownValue)));
        countdownTimer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                countdownValue--;
                Platform.runLater(() -> computerChoiceLabel.setText("Computer: " + String.valueOf(countdownValue)));

                if (countdownValue == 0) {
                    revealComputerChoice();
                    countdownTimer.stop();
                }
            }
        });
        countdownTimer.start();
    }

    private void revealComputerChoice() {
        int randomIndex = (int) (Math.random() * choices.length);
        String computerChoice = choices[randomIndex];
        Platform.runLater(() -> {
                    computerChoiceLabel.setText("Computer: " + computerChoice);
                    playerChoiceLabel.setText("Spieler: " + playerChoice);
                }
        );
        String res = determineWinner(computerChoice);
        Platform.runLater(() -> resultLabel.setText(res));
        rockButton.setDisable(false);
        paperButton.setDisable(false);
        scissorsButton.setDisable(false);
    }

    private String determineWinner(String computerChoice) {
        System.out.println("Player: " + playerChoice + ", Computer: " + computerChoice);
        if (playerChoice.equals(computerChoice)) {
            return "Unentschieden!";
        } else if ((playerChoice.equals("Schere") && computerChoice.equals("Papier")) ||
                (playerChoice.equals("Stein") && computerChoice.equals("Schere")) ||
                (playerChoice.equals("Papier") && computerChoice.equals("Stein"))) {
            return "Gewonnen!";
        } else {
            return "Verloren!";
        }
    }
}
