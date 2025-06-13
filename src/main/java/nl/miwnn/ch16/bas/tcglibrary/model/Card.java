package nl.miwnn.ch16.bas.tcglibrary.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

/**
 * @author Bas Folkers
 * The concept of a card for which the library can have a copy
 */

@Entity
public class Card {
    @Id @GeneratedValue
    private Long cardId;

    private String name;

    @ManyToOne
    private Expansion expansion;

    public Card(Expansion expansion) {
        this.expansion = expansion;
    }

//    exists for JPA
    public Card() {
    }

    @Override
    public String toString() {
        return String.format("%s ", this.name);
    }

    public Long getCardId() {
        return cardId;
    }

    public void setCardId(Long cardId) {
        this.cardId = cardId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Expansion getExpansion() {
        return expansion;
    }

    public void setExpansion(Expansion expansion) {
        this.expansion = expansion;
    }
}
