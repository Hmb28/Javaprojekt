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

    public Player(String name) {
        this.name = name;
        this.hand = new ArrayList<>();
        this.betAmount = 0;
        this.isBust = false;
        this.blackjack = false;
        this.doubleDown = false;
    }

    public void addCard(Card card) {
        hand.add(card);
        updateHandValue();
        checkBlackjack();
        checkBust();
    }

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

    public boolean isBust() {
        return isBust;
    }

    public boolean isBlackjack() {
        return blackjack;
    }

    public boolean canDoubleDown() {
        return hand.size() == 2 && !isBust && !blackjack;
    }

    public void doubleDown() {
        doubleDown = true;
        betAmount *= 2;
    }

    public int getBetAmount() {
        return betAmount;
    }

    public void setBetAmount(int betAmount) {
        this.betAmount = betAmount;
    }

    private void updateHandValue() {
        // Hand value is calculated in getHandValue()
    }

    private void checkBlackjack() {
        if (hand.size() == 2 && getHandValue() == 21) {
            blackjack = true;
        }
    }

    private void checkBust() {
        isBust = getHandValue() > 21;
    }

    @Override
    public String toString() {
        return name + ": " + hand + ", Hand Value: " + getHandValue();
    }
}