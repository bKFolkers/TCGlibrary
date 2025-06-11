package nl.miwnn.ch16.bas.tcglibrary.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

/**
 * @author Bas Folkers
 * The concept of a card for which the library can have a copy
 */

@Entity
public class Card {
    @Id @GeneratedValue
    private Long cardId;

    private String name;
    private String expansion;

    @Override
    public String toString() {
        return String.format("%s - %s", this.name, this.expansion);
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

    public String getExpansion() {
        return expansion;
    }

    public void setExpansion(String expansion) {
        this.expansion = expansion;
    }
}
