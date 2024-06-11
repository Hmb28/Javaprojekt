package de.hmb.pp.test.blackjack;


import javafx.scene.image.Image;

import java.io.FileInputStream;

public class Card {

    private Suit suit;
    private Rank rank;
    private int value;
    private Image image;

    public Card(Suit suit, Rank rank) {
        this.suit = suit;
        this.rank = rank;
        this.value = rank.getValue();
        System.out.println((rank.value > 10 ? rank.toString().toLowerCase() : rank.value) + "_of_" + suit.toString().toLowerCase() + ".png");
        try {
            FileInputStream inputstream = new FileInputStream("C:\\Users\\hmb\\Downloads\\PNG-cards-1.3\\PNG-cards-1.3\\" + (rank.value > 10 ? rank.toString().toLowerCase() : rank.value) + "_of_" + suit.toString().toLowerCase() + ".png");
            this.image = new Image(inputstream);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Suit getSuit() {
        return suit;
    }

    public Rank getRank() {
        return rank;
    }

    public int getValue() {
        return value;
    }

    public Image getImage() {
        return image;
    }

    private String getCardImageURL() {
        System.out.println("resources/cards/" + rank.toString().toLowerCase() + "_of_" + suit.toString().toLowerCase() + ".png");
        return "/resources/cards/" + rank.value + "_of_" + suit.toString().toLowerCase() + ".png";
    }

    @Override
    public String toString() {
        return rank + " of " + suit;
    }

    public enum Suit {
        CLUBS, DIAMONDS, HEARTS, SPADES
    }

    public enum Rank {
        ACE(11), TWO(2), THREE(3), FOUR(4), FIVE(5), SIX(6), SEVEN(7), EIGHT(8), NINE(9), TEN(10), JACK(10), QUEEN(10), KING(10);

        private int value;

        Rank(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }
}
