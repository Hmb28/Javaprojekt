package de.hmb.pp.test.util;

public class EmptyDeckException extends RuntimeException {

    public EmptyDeckException() {
        super("Deck is empty. Cannot draw a card.");
    }
}