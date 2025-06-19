package nl.miwnn.ch16.bas.tcglibrary.model;

import jakarta.persistence.*;

import java.util.List;

/**
 * @author Bas Folkers
 * Represents a collection of cards that can be used to play in trading card matches
 */

@Entity
public class Deck {
    public static final int MAX_NUMBER_OF_CARDS_IN_DECK = 60;

    @Id @GeneratedValue
    private Long deckId;

    private String name;
    private Double deckPrice;

    private Integer numberOfCardsInDeck;


    @ManyToMany
    private List<Card> cards;

    public String getAddedCardsStatus() {
        int added = (numberOfCardsInDeck == null) ? 0 : numberOfCardsInDeck;
        return added + "/" + MAX_NUMBER_OF_CARDS_IN_DECK;
    }

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

    public Double getDeckPrice() {
        if (cards == null) {
            return 0.0;
        }

        return cards.stream()
                .filter(card -> card.getMarketPrice() != null)
                .mapToDouble(Card::getMarketPrice)
                .sum();
    }

    public void setDeckPrice(Double price) {
        this.deckPrice = price;
    }

    public List<Card> getCards() {
        return cards;
    }

    public void setCards(List<Card> cards) {
        this.cards = cards;
    }
}
