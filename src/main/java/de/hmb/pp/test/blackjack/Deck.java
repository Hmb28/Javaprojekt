package de.hmb.pp.test.blackjack;

import de.hmb.pp.test.util.EmptyDeckException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Deck {

    private List<Card> cards;
    private Random random = new Random();

    /**
     * Constructor for a deck of cards.
     * Initializes the deck with all 52 cards and shuffles them.
     */
    public Deck() {
        cards = new ArrayList<>();
        for (Card.Suit suit : Card.Suit.values()) {
            for (Card.Rank rank : Card.Rank.values()) {
                cards.add(new Card(suit, rank));
            }
        }
        shuffle();
    }

    /**
     * Shuffles the deck of cards.
     */
    public void shuffle() {
        Collections.shuffle(cards, random);
    }

    /**
     * Draws a card from the deck.
     * @return the random card drawn from the deck
     */
    public Card drawCard() {
        if (cards.isEmpty()) {
            throw new EmptyDeckException();
        }
        return cards.remove(0);
    }

    /**
     * Returns the number of cards left in the deck.
     * @return the number of cards left in the deck
     */
    public int size() {
        return cards.size();
    }

    /**
     * Checks if the deck is empty.
     * @return true if the deck is empty, false otherwise
     */
    public boolean isEmpty() {
        return cards.isEmpty();
    }

    /**
     * Returns a string representation of the deck.
     * @return a string representation of the deck
     */
    @Override
    public String toString() {
        return "Deck: " + cards;
    }
}
