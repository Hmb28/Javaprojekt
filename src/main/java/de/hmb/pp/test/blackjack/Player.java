package de.hmb.pp.test.blackjack;

import java.util.ArrayList;
import java.util.List;

public class Player {

    private String name;
    private List<Card> hand;
    private int betAmount;
    private boolean isBust;
    private boolean blackjack;
    private boolean doubleDown;

    /**
     * Constructor for a player
     * @param name the name of the player
     */
    public Player(String name) {
        this.name = name;
        this.hand = new ArrayList<>();
        this.betAmount = 0;
        this.isBust = false;
        this.blackjack = false;
        this.doubleDown = false;
    }

    /**
     * Add a card to the player's hand
     * @param card the card to add to the player's hand
     */
    public void addCard(Card card) {
        hand.add(card);
        updateHandValue();
        checkBlackjack();
        checkBust();
    }

    /**
     * Get the value of the player's hand
     * @return the value of the player's hand
     */
    public int getHandValue() {
        int handValue = 0;
        for (Card card : hand) {
            handValue += card.getValue();
        }

        // Adjust for Aces
        for (Card card : hand) {
            if (card.getValue() == 11 && handValue > 21) {
                handValue -= 10;
            }
        }

        return handValue;
    }

    /**
     * Get if the player is bust
     * @return true if the player is bust, false otherwise
     */
    public boolean isBust() {
        return isBust;
    }

    /**
     * Get if the player has blackjack
     * @return true if the player has blackjack, false otherwise
     */
    public boolean isBlackjack() {
        return blackjack;
    }

    /**
     * Get if the player can double down
     * @return true if the player can double down, false otherwise
     */
    public boolean canDoubleDown() {
        return hand.size() == 2 && !isBust && !blackjack;
    }

    /**
     * Double down the player's bet
     */
    public void doubleDown() {
        doubleDown = true;
        betAmount *= 2;
    }

    /**
     * Get the current bet amount
     * @return the current bet amount
     */
    public int getBetAmount() {
        return betAmount;
    }

    /**
     * Set the bet amount
     * @param betAmount the bet amount to set
     */
    public void setBetAmount(int betAmount) {
        this.betAmount = betAmount;
    }

    /**
     * Get the player's hand
     * @return the player's hand
     */
    @Deprecated
    private void updateHandValue() {
        // Hand value is calculated in getHandValue()
    }

    /**
     * Check if the player has blackjack
     */
    private void checkBlackjack() {
        if (hand.size() == 2 && getHandValue() == 21) {
            blackjack = true;
        }
    }

    /**
     * Check if the player is bust
     */
    private void checkBust() {
        isBust = getHandValue() > 21;
    }

    /**
     * Return the player's hand as a string
     * @return the player's hand as a string
     */
    @Override
    public String toString() {
        return name + ": " + hand + ", Hand Value: " + getHandValue();
    }
}