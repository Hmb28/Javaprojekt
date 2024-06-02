package de.hmb.pp.test;

public class EmptyDeckException extends RuntimeException {

    public EmptyDeckException() {
        super("Deck is empty. Cannot draw a card.");
    }
}