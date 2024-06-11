package de.hmb.pp.test.util;

public class EmptyDeckException extends RuntimeException {

    /**
     * Constructor for an empty deck exception.
     */
    public EmptyDeckException() {
        super("Deck is empty. Cannot draw a card.");
    }
}