package de.hmb.pp.test.blackjack;

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

    /**
     * Initializes the game state and deals the initial cards.
     */
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

    /**
     * Deals the initial cards to the dealer and player.
     * Checks for blackjack on initial deal.
     */
    private void dealInitialCards() {
        dealCard(dealer);
        dealCard(player);
        dealCard(dealer);
        dealCard(player);

        // Check for blackjack on initial deal
        checkBlackjack();
    }

    /**
     * Deals a card to the specified player and adds the card to the player's hand.
     * Also adds the card to the appropriate grid for display.
     * @param player The player to deal a card to
     */
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

    /**
     * Updates the hand values for the dealer and player and updates the game status.
     * Also checks for win conditions.
     */
    private void updateHandValuesAndStatus() {
        dealerHandValueLabel.setText("Dealer Hand: " + dealer.getHandValue());
        playerHandValueLabel.setText("Player Hand: " + player.getHandValue());

        updateGameStatus();
    }

    /**
     * Starts a new game by resetting the game state and dealing initial cards.
     */
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

    /**
     * Updates the game status based on the current hand values of the dealer and player.
     */
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

    /**
     * Checks for blackjack on the initial deal.
     * If the dealer has blackjack, the player loses.
     * If the player has blackjack, the player wins.
     * If both have blackjack, it is a push.
     * If neither have blackjack, the game continues.
     */
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

    /**
     * Deals a card to the player and updates the hand values and game status.
     * Also checks for bust after dealing a card.
      */
    @FXML
    public void hitButtonPress() {
        if (gameInProgress) {
            dealCard(player);
            updateHandValuesAndStatus();
            checkBust();
        }
    }

    /**
     * Ends the player's turn and starts the dealer's turn.
     * The dealer will continue to draw cards until their hand value is 17 or higher.
     */
    @FXML
    public void standButtonPress() {
        if (gameInProgress) {
            dealerTurn();
            updateHandValuesAndStatus();
            checkWinConditions();
        }
    }

    /**
     * Doubles the player's bet and deals one more card to the player.
     * The player's turn ends after doubling down.
     */
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

    /**
     * The dealer's turn. The dealer will continue to draw cards until their hand value is 17 or higher.
     */
    private void dealerTurn() {
        while (dealer.getHandValue() < 17) {
            dealCard(dealer);
        }
    }

    /**
     * Checks if the player has busted.
     */
    private void checkBust() {
        if (player.isBust()) {
            gameInProgress = false;
            updateGameStatus();
        }
    }

    /**
     * Checks the win conditions of the game.
     */
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

    /**
     * Disables the hit, stand, and double down buttons.
     */
    private void disableButtons() {
        hitButton.setDisable(true);
        standButton.setDisable(true);
        doubleDownButton.setDisable(true);
    }

    /**
     * Enables the hit, stand, and double down buttons.

     */
    private void enableButtons() {
        hitButton.setDisable(false);
        standButton.setDisable(false);
        doubleDownButton.setDisable(false);
    }
}
