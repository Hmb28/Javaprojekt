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

    /**
     * Handles the click event for the Schere button.
     */
    @FXML
    protected void onSchereClick() {
        System.out.println("Schere");
        playerChoice = "Schere";
        handleReveal();
    }

    /**
     * Handles the click event for the Stein button.
     */
    @FXML
    protected void onSteinClick() {
        System.out.println("Stein");
        playerChoice = "Stein";
        handleReveal();
    }

    /**
     * Handles the click event for the Papier button.
     */
    @FXML
    protected void onPapierClick() {
        System.out.println("Papier");
        playerChoice = "Papier";
        handleReveal();
    }

    /**
     * Handles the reveal of the computer's choice.
     * Disables the buttons during the countdown.
     * Updates the labels with the player's and computer's choices.
     * Starts a timer that counts down from 3 to 0.
     * When the countdown reaches 0, the computer's choice is revealed.
     */
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

    /**
     * Reveals the computer's choice and determines the winner.
     * Re-enables the buttons.
     * Updates the labels with the player's and computer's choices.
     */
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

    /**
     * Determines the winner of the game based on the player's and computer's choices.
     * @param computerChoice the computer's choice
     * @return the result of the game
     */
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
