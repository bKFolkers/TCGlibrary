package nl.miwnn.ch16.bas.tcglibrary.controller;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import nl.miwnn.ch16.bas.tcglibrary.model.Card;
import nl.miwnn.ch16.bas.tcglibrary.model.Deck;
import nl.miwnn.ch16.bas.tcglibrary.model.Expansion;
import nl.miwnn.ch16.bas.tcglibrary.repositories.CardRepository;
import nl.miwnn.ch16.bas.tcglibrary.repositories.DeckRepository;
import nl.miwnn.ch16.bas.tcglibrary.repositories.ExpansionRepository;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

/**
 * @author Bas Folkers
 * Set some initial data in the database for (manual) testing purposes
 */

@Controller
public class InitializeController {
    private final CardRepository cardRepository;
    private final ExpansionRepository expansionRepository;
    private final DeckRepository deckRepository;

    private final Map<String, Expansion> expansionCache = new HashMap<>();

    public InitializeController(CardRepository cardRepository,
                                ExpansionRepository expansionRepository,
                                DeckRepository deckRepository) {
        this.cardRepository = cardRepository;
        this.expansionRepository = expansionRepository;
        this.deckRepository = deckRepository;
    }

    @EventListener
    private void seed(ContextRefreshedEvent ignoredEvent){
        if (expansionRepository.count() == 0) {
            initializeDB();
        }
    }

    public void initializeDB() {
        try {
            loadExpansions();
            loadCards();
            loadDecks();
        } catch (IOException | CsvValidationException e) {
            throw new RuntimeException("Failed to initialize database from CSV files", e);
        }
    }

    private void loadExpansions() throws IOException, CsvValidationException {
        try (CSVReader reader = new CSVReader(new InputStreamReader(
                new ClassPathResource("/tcg_data/expansions/baseSetExpansion.csv").getInputStream()))) {

            reader.skip(1);

            for (String[] expansionLine : reader) {
                Expansion expansion = new Expansion();

                expansion.setName(expansionLine[0]);
                expansion.setDescription(expansionLine[1]);
                expansion.setReleaseDate(LocalDate.parse(expansionLine[2]));
                expansion.setNumberOfCards(Integer.valueOf(expansionLine[3]));
                expansion.setImageUrl(expansionLine[4]);

                expansionRepository.save(expansion);
                expansionCache.put(expansion.getName(), expansion);
            }
        }
    }

    private void loadCards() throws IOException, CsvValidationException {
        try (CSVReader reader = new CSVReader(new InputStreamReader(
                new ClassPathResource("/tcg_data/cards/baseSetCards.csv").getInputStream()))) {

            reader.skip(1);

            for (String[] cardLine : reader) {
                Card card = new Card();
                card.setName(cardLine[0]);
                card.setRarity(cardLine[1]);
                card.setSubTypeName(cardLine[2]);
                card.setMarketPrice(Double.parseDouble(cardLine[3]));
                card.setModifiedOn(LocalDateTime.parse(cardLine[4]));
                card.setImageUrl(cardLine[5]);


                String expansionName = cardLine[6].trim(); // pas dit aan als nodig
                Expansion expansion = expansionCache.get(expansionName);

                if (expansion != null) {
                    card.setExpansion(expansion); // werkt als Card één Expansion heeft
                } else {
                    System.err.println("Expansion not found: " + expansionName);
                    continue; // sla deze kaart over
                }

                cardRepository.save(card);
            }
        }
    }

    private void loadDecks() throws IOException, CsvValidationException {
        try (CSVReader reader = new CSVReader(new InputStreamReader(
                new ClassPathResource("/tcg_data/decks/decks.csv").getInputStream()))) {

            reader.skip(1);

            for (String[] decksLine : reader) {
                Deck deck = new Deck();

                deck.setName(decksLine[0]);

                deckRepository.save(deck);
            }
        }
    }
}
