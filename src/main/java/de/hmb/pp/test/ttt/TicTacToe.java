package de.hmb.pp.test.ttt;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

import java.util.Stack;

public class TicTacToe {

    @FXML
    private GridPane boardGrid;
    @FXML
    private Label titleLabel;
    @FXML
    private Label statusLabel;

    @FXML
    private Button undoButton;

    private Button[][] buttons;
    private String currentPlayer = "X";
    private Stack<String> moves;

    @FXML
    public void initialize() {
        buttons = new Button[3][3];
        moves = new Stack<>();
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                buttons[row][col] = (Button) boardGrid.getChildren().get(row * 3 + col);
                int finalRow = row;
                int finalCol = col;
                buttons[row][col].setOnAction(event -> handleButtonPress(finalRow, finalCol));
            }
        }
    }

    @FXML
    protected void resetGame() {
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                buttons[row][col].setText("");
                buttons[row][col].setDisable(false);
            }
        }
        statusLabel.setText("");
        currentPlayer = "X";
        undoButton.setDisable(false);
        resetStyles();
    }

    @FXML
    protected void undo() {
        if (moves.isEmpty()) {
            return;
        }
        String lastMove = moves.pop();
        String[] parts = lastMove.split(",");
        int row = Integer.parseInt(parts[0]);
        int col = Integer.parseInt(parts[1]);
        buttons[row][col].setText("");
        togglePlayer();
    }

    private void handleButtonPress(int row, int col) {
        System.out.println("Button pressed: " + row + ", " + col);
        if (!buttons[row][col].getText().isEmpty()) {
            return; // Ignore clicks on already marked squares
        }
        buttons[row][col].setText(currentPlayer);
        moves.push(row + "," + col);
        checkGameState();
        togglePlayer();
    }

    private void checkGameState() {
        // Check for horizontal, vertical, and diagonal wins
        for (int i = 0; i < 3; i++) {
            if (checkLine(buttons[i][0], buttons[i][1], buttons[i][2])) {
                gameOver(currentPlayer);
                return;
            }
            if (checkLine(buttons[0][i], buttons[1][i], buttons[2][i])) {
                gameOver(currentPlayer);
                return;
            }
        }

        // Check for diagonal wins
        if (checkLine(buttons[0][0], buttons[1][1], buttons[2][2])) {
            gameOver(currentPlayer);
            return;
        }
        if (checkLine(buttons[0][2], buttons[1][1], buttons[2][0])) {
            gameOver(currentPlayer);
            return;
        }

        // Check for a tie
        if (isBoardFull()) {
            gameOver("Tie");
        }
    }

    private boolean checkLine(Button button1, Button button2, Button button3) {
        boolean w = button1.getText().equals(currentPlayer) && button2.getText().equals(currentPlayer) && button3.getText().equals(currentPlayer);
        if (w) {
            markWinningLine(button1, button2, button3);
        }
        return w;
    }

    private boolean isBoardFull() {
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                if (buttons[row][col].getText().isEmpty()) {
                    return false;
                }
            }
        }
        return true;
    }

    private void resetStyles() {
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                buttons[row][col].setStyle("");
            }
        }
    }

    private void markWinningLine(Button button1, Button button2, Button button3) {
        button1.setStyle("-fx-background-color: lightgreen;");
        button2.setStyle("-fx-background-color: lightgreen;");
        button3.setStyle("-fx-background-color: lightgreen;");
    }

    private void gameOver(String winner) {
        statusLabel.setText("Game Over! Winner: " + winner);
        disableButtons();
    }

    private void togglePlayer() {
        if (currentPlayer.equals("X")) {
            currentPlayer = "O";
        } else {
            currentPlayer = "X";
        }
    }

    private void disableButtons() {
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                buttons[row][col].setDisable(true);
            }
        }
        undoButton.setDisable(true);
    }
}

