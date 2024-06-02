package de.hmb.pp.test;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

public class BlackJack {

    @FXML
    private Label titleLabel;
    @FXML
    private GridPane dealerCardsGrid;
    @FXML
    private Label dealerHandValueLabel;
    @FXML
    private GridPane playerCardsGrid;
    @FXML
    private Label playerHandValueLabel;
    @FXML
    private Button hitButton;
    @FXML
    private Button standButton;
    @FXML
    private Button doubleDownButton;
    @FXML
    private Label gameStatusLabel;

    private Deck deck;
    private Player dealer;
    private Player player;
    private boolean gameInProgress;

    @FXML
    public void initialize() {
        deck = new Deck();
        deck.shuffle();

        dealer = new Player("Dealer");
        player = new Player("Player");

        gameInProgress = true;

        dealInitialCards();
        updateHandValuesAndStatus();
    }

    private void dealInitialCards() {
        dealCard(dealer);
        dealCard(player);
        dealCard(dealer);
        dealCard(player);

        // Check for blackjack on initial deal
        checkBlackjack();
    }

    private void dealCard(Player player) {
        Card card = deck.drawCard();
        player.addCard(card);

        ImageView cardImageView = new ImageView(card.getImage());
        cardImageView.setFitWidth(100);
        cardImageView.setFitHeight(150);

        if (player == dealer) {
            dealerCardsGrid.add(cardImageView, dealerCardsGrid.getChildren().size(), 0);
        } else {
            playerCardsGrid.add(cardImageView, playerCardsGrid.getChildren().size(), 0);
        }
    }

    private void updateHandValuesAndStatus() {
        dealerHandValueLabel.setText("Dealer Hand: " + dealer.getHandValue());
        playerHandValueLabel.setText("Player Hand: " + player.getHandValue());

        updateGameStatus();
    }

    public void newGameButtonPress() {
        // Reset game state
        deck = new Deck();
        deck.shuffle();

        player = new Player("Player");
        dealer = new Player("Dealer");

        gameInProgress = true;
        dealerCardsGrid.getChildren().clear();
        playerCardsGrid.getChildren().clear();

        gameStatusLabel.setText("");

        dealInitialCards();
        updateHandValuesAndStatus();
        enableButtons();
    }


    private void updateGameStatus() {
        if (!gameInProgress) {
            String message;
            if (player.isBust()) {
                message = "You Busted! Dealer Wins.";
            } else if (dealer.isBust()) {
                message = "Dealer Busted! You Win!";
            } else if (player.getHandValue() > dealer.getHandValue()) {
                message = "You Win!";
            } else if (player.getHandValue() == dealer.getHandValue()) {
                message = "Push.";
            } else {
                message = "Dealer Wins.";
            }

            gameStatusLabel.setText(message);

            disableButtons();
        }
    }

    private void checkBlackjack() {
        if (dealer.isBlackjack()) {
            gameStatusLabel.setText("Dealer Blackjack! You Lose.");
            gameInProgress = false;
            disableButtons();
        } else if (player.isBlackjack()) {
            gameStatusLabel.setText("Blackjack! You Win!");
            gameInProgress = false;
            disableButtons();
        }
    }

    @FXML
    public void hitButtonPress() {
        if (gameInProgress) {
            dealCard(player);
            updateHandValuesAndStatus();
            checkBust();
        }
    }

    @FXML
    public void standButtonPress() {
        if (gameInProgress) {
            dealerTurn();
            updateHandValuesAndStatus();
            checkWinConditions();
        }
    }

    @FXML
    public void doubleDownButtonPress() {
        if (gameInProgress && player.canDoubleDown()) {
            player.doubleDown();
            dealCard(player);
            updateHandValuesAndStatus();

            // Dealer's turn automatically after double down
            dealerTurn();
            checkWinConditions();
        }
    }

    private void dealerTurn() {
        while (dealer.getHandValue() < 17) {
            dealCard(dealer);
        }
    }

    private void checkBust() {
        if (player.isBust()) {
            gameInProgress = false;
            updateGameStatus();
        }
    }

    private void checkWinConditions() {
        if (!gameInProgress) {
            return;
        }

        if (player.isBust()) {
            gameStatusLabel.setText("You Busted! Dealer Wins.");
        } else if (dealer.isBust()) {
            gameStatusLabel.setText("Dealer Busted! You Win!");
        } else if (player.getHandValue() > dealer.getHandValue()) {
            gameStatusLabel.setText("You Win!");
        } else if (player.getHandValue() == dealer.getHandValue()) {
            gameStatusLabel.setText("Push.");
        } else {
            gameStatusLabel.setText("Dealer Wins.");
        }

        gameInProgress = false;
        disableButtons();
    }

    private void disableButtons() {
        hitButton.setDisable(true);
        standButton.setDisable(true);
        doubleDownButton.setDisable(true);
    }

    private void enableButtons() {
        hitButton.setDisable(false);
        standButton.setDisable(false);
        doubleDownButton.setDisable(false);
    }
}
