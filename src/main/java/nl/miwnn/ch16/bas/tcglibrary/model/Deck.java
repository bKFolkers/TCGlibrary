package nl.miwnn.ch16.bas.tcglibrary.model;

import jakarta.persistence.*;

import java.util.List;

/**
 * @author Bas Folkers
 * Represents a collection of cards that can be used to play in trading card matches
 */

@Entity
public class Deck {
    @Id @GeneratedValue
    private Long deckId;

    private String name;
    private Double price;

    private Integer numberOfCardsInDeck;

    @ManyToMany
    private List<Card> cards;

    public Integer getNumberOfCardsInDeck() {
        return numberOfCardsInDeck;
    }

    public void setNumberOfCardsInDeck(Integer numberOfCardsInDeck) {
        this.numberOfCardsInDeck = numberOfCardsInDeck;
    }

    public Long getDeckId() {
        return deckId;
    }

    public void setDeckId(Long deckId) {
        this.deckId = deckId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Card> getCards() {
        return cards;
    }

    public void setCards(List<Card> cards) {
        this.cards = cards;
    }
}
