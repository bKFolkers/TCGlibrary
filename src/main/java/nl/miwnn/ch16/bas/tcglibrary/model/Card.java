package nl.miwnn.ch16.bas.tcglibrary.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author Bas Folkers
 * The concept of a card for which the library can have a copy
 */

@Entity
public class Card {
    @Id @GeneratedValue
    private Long cardId;

    @Column(unique = true)
    private String name;

    private String rarity;
    private String subTypeName;
    private Double marketPrice;
    private LocalDateTime modifiedOn = LocalDateTime.now();
    private String imageUrl;


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

    public String getRarity() {
        return rarity;
    }

    public void setRarity(String rarity) {
        this.rarity = rarity;
    }

    public String getSubTypeName() {
        return subTypeName;
    }

    public void setSubTypeName(String subTypeName) {
        this.subTypeName = subTypeName;
    }

    public Double getMarketPrice() {
        if(marketPrice == null){
            return marketPrice = 0.0;
        }
        return marketPrice;
    }

    public void setMarketPrice(Double marketPrice) {
        this.marketPrice = marketPrice;
    }

    public LocalDateTime getModifiedOn() {
        return modifiedOn;
    }

    public void setModifiedOn(LocalDateTime modifiedOn) {
        this.modifiedOn = modifiedOn;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Expansion getExpansion() {
        return expansion;
    }

    public void setExpansion(Expansion expansion) {
        this.expansion = expansion;
    }
}
