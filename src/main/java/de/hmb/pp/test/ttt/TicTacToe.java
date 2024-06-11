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

    /**
     * Initializes the game board and sets up the button event handlers.
     * This method is called automatically by JavaFX after the FXML file has been loaded.
     * It is used to initialize the game state and set up the event handlers for the buttons.
     */
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

    /**
     * Resets the game board to its initial state.
     * This method is called when the "Reset" button is clicked.
     */
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

    /**
     * Undoes the last move made by a player.
     * This method is called when the "Undo" button is clicked.
     */
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

    /**
     * Handles the button press event for a specific button on the game board.
     * @param row the row of the button that was pressed
     * @param col the column of the button that was pressed
     */
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

    /**
     * Checks the current state of the game to determine if there is a winner or a tie.
     * If a winner is found, the game is ended and the winning player is displayed.
     */
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

    /**
     * Checks if a line of buttons contains the same player symbol.
     * @param button1 the first button in the line
     * @param button2 the second button in the line
     * @param button3 the third button in the line
     * @return true if the line contains the same player symbol, false otherwise
     */
    private boolean checkLine(Button button1, Button button2, Button button3) {
        boolean w = button1.getText().equals(currentPlayer) && button2.getText().equals(currentPlayer) && button3.getText().equals(currentPlayer);
        if (w) {
            markWinningLine(button1, button2, button3);
        }
        return w;
    }

    /**
     * Checks if the game board is full.
     * @return true if the board is full, false otherwise
     */
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

    /**
     * Resets the styles of all buttons on the game board.
     * This method is used to remove any highlighting of winning lines.
     */
    private void resetStyles() {
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                buttons[row][col].setStyle("");
            }
        }
    }

    /**
     * Marks the winning line of buttons with a light green background color.
     * @param button1 the first button in the winning line
     * @param button2 the second button in the winning line
     * @param button3 the third button in the winning line
     */
    private void markWinningLine(Button button1, Button button2, Button button3) {
        button1.setStyle("-fx-background-color: lightgreen;");
        button2.setStyle("-fx-background-color: lightgreen;");
        button3.setStyle("-fx-background-color: lightgreen;");
    }

    /**
     * Ends the game and displays the winner.
     * @param winner the winning player or "Tie" if the game ended in a tie
     */
    private void gameOver(String winner) {
        statusLabel.setText("Game Over! Winner: " + winner);
        disableButtons();
    }

    /**
     * Toggles the current player between "X" and "O".
     * This method is used to switch the player after each move.
     */
    private void togglePlayer() {
        if (currentPlayer.equals("X")) {
            currentPlayer = "O";
        } else {
            currentPlayer = "X";
        }
    }

    /**
     * Disables all buttons on the game board.
     * This method is used to prevent further moves after the game has ended.
     */
    private void disableButtons() {
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                buttons[row][col].setDisable(true);
            }
        }
        undoButton.setDisable(true);
    }
}

