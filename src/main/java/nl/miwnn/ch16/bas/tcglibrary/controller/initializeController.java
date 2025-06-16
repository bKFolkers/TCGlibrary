package nl.miwnn.ch16.bas.tcglibrary.controller;

import nl.miwnn.ch16.bas.tcglibrary.model.Card;
import nl.miwnn.ch16.bas.tcglibrary.model.Deck;
import nl.miwnn.ch16.bas.tcglibrary.model.Expansion;
import nl.miwnn.ch16.bas.tcglibrary.repositories.CardRepository;
import nl.miwnn.ch16.bas.tcglibrary.repositories.DeckRepository;
import nl.miwnn.ch16.bas.tcglibrary.repositories.ExpansionRepository;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Bas Folkers
 * Set some initial data in the database for (manual) testing purposes
 */

public class initializeController {
    private final CardRepository cardRepository;
    private final ExpansionRepository expansionRepository;
    private final DeckRepository deckRepository;

    public initializeController(CardRepository cardRepository,
                                ExpansionRepository expansionRepository,
                                DeckRepository deckRepository) {
        this.cardRepository = cardRepository;
        this.expansionRepository = expansionRepository;
        this.deckRepository = deckRepository;
    }

    @EventListener
    private void seed(ContextRefreshedEvent ignoredEvent){
        if (expansionRepository.count() > 0) {
            initializeDB();
        }
    }

    public void initializeDB() {
        Expansion baseSet = makeExpansion("Base set",
                LocalDate.of(1999, 1, 9), 102);
        Expansion jungle = makeExpansion("Jungle",
                LocalDate.of(1999, 6, 16), 64);
        Expansion fossil = makeExpansion("Fossil",
                LocalDate.of(1999, 10, 10), 62);
        Expansion baseSet2 = makeExpansion("Base Set 2",
                LocalDate.of(2000, 2, 24), 130);
        Expansion teamRocket = makeExpansion("Team Rocket",
                LocalDate.of(2000, 4, 24), 83);
        Expansion gymHeroes = makeExpansion("Gym Heroes",
                LocalDate.of(2000, 8, 14), 132);
        Expansion gymChallenges = makeExpansion("Gym Challenges",
                LocalDate.of(2000, 10, 16), 132);

        Deck roaringMoon = makeDeck("Roaring Moon");
        Deck gardevoir = makeDeck("Gardevoir");

        Card pikachu = makeCard("Pikachu");
        Card blastoise = makeCard("Blastoise");
        Card charizard = makeCard("Charizard");
        Card venusaur = makeCard("Venusaur");
        Card Ratata = makeCard("Ratata");
        Card diglet = makeCard("Diglet");
    }

    private Expansion makeExpansion(String name, LocalDate releaseDate, Integer numberOfCards) {
        Expansion expansion = new Expansion();
        expansion.setName(name);
        expansion.setReleaseDate(releaseDate);
        expansion.setNumberOfCards(numberOfCards);
        expansionRepository.save(expansion);
        return expansion;
    }

    private Card makeCard(String name) {
        Card card = new Card();

        card.setName(name);


        cardRepository.save(card);
        return card;
    }

    private Deck makeDeck(String name, Card ... cards) {
        Deck deck = new Deck();

        deck.setName(name);

        List<Card> cardList = new ArrayList<>(Arrays.asList(cards));
        deck.setCards(cardList);

        deckRepository.save(deck);
        return deck;
    }
}
