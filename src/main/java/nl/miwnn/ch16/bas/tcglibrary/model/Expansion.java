package nl.miwnn.ch16.bas.tcglibrary.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Bas Folkers
 * Represents an expansion in which new cards are released
 */

@Entity
public class Expansion {
    @Id @GeneratedValue
    private Long expansionId;

    private String name;
    private LocalDate releaseDate;
    private Integer numberOfCards;
    private String imageUrl;

    private Integer numberOfAddedCards;

    private Double expansionWorth;

    @OneToMany(mappedBy = "expansion")
    private List<Card> cards = new ArrayList<>();

    public Expansion(String name, LocalDate releaseDate, Integer numberOfCards) {
        this.name = name;
        this.releaseDate = releaseDate;
        this.numberOfCards = numberOfCards;
    }

    public Expansion() {
    }

    @Override
    public String toString() {
        return String.format("%s", this.name);
    }

    public String getAddedCardsStatus() {
        int added = (numberOfAddedCards == null) ? 0 : numberOfAddedCards;
        int total = (numberOfCards == null) ? 0 : numberOfCards;
        return added + "/" + total;
    }

    public Integer getNumberOfAddedCards() {
        return numberOfAddedCards;
    }

    public void setNumberOfAddedCards(Integer numberOfAddedCards) {
        this.numberOfAddedCards = numberOfAddedCards;
    }

    public Long getExpansionId() {
        return expansionId;
    }

    public void setExpansionId(Long expansionId) {
        this.expansionId = expansionId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    public Integer getNumberOfCards() {
        return numberOfCards;
    }

    public void setNumberOfCards(Integer numberOfCards) {
        this.numberOfCards = numberOfCards;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Double getExpansionWorth() {
        if (cards == null) {
            return 0.0;
        }

        double sum = cards.stream()
                .filter(card -> card.getMarketPrice() != null)
                .mapToDouble(Card::getMarketPrice)
                .sum();

        return Math.round(sum * 100.0) / 100.0;
    }

    public void setExpansionWorth(Double expansionWorth) {
        this.expansionWorth = expansionWorth;
    }

    public List<Card> getCards() {
        return cards;
    }

    public void setCards(List<Card> cards) {
        this.cards = cards;
    }
}
