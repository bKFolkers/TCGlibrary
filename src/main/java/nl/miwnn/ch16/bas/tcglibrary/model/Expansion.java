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

    @OneToMany(mappedBy = "expansion", cascade = CascadeType.ALL)
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

    public Integer numberOfAddedCards() {
        return cards.size();
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

    public List<Card> getCards() {
        return cards;
    }

    public void setCards(List<Card> cards) {
        this.cards = cards;
    }
}
