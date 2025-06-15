package nl.miwnn.ch16.bas.tcglibrary.controller;

import nl.miwnn.ch16.bas.tcglibrary.model.Deck;
import nl.miwnn.ch16.bas.tcglibrary.model.Expansion;
import nl.miwnn.ch16.bas.tcglibrary.repositories.CardRepository;
import nl.miwnn.ch16.bas.tcglibrary.repositories.DeckRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/**
 * @author Bas Folkers
 * Handles all requests related to decks of cards
 */

@Controller
@RequestMapping("/deck")
public class DeckController {
    private final CardRepository cardRepository;
    private final DeckRepository deckRepository;

    public DeckController(CardRepository cardRepository, DeckRepository deckRepository) {
        this.cardRepository = cardRepository;
        this.deckRepository = deckRepository;
    }

    @GetMapping("/overview")
    private String showDeckOverview(Model datamodel) {
        datamodel.addAttribute("allDecks", deckRepository.findAll());
        datamodel.addAttribute("formDeck", new Deck());

        return "deckOverview";
    }

    @PostMapping("/save")
    private String saveNewDeck(@ModelAttribute("formDeck") Deck deckToBeSaved, BindingResult bindingResult) {
        if (!bindingResult.hasErrors()) {
            deckRepository.save(deckToBeSaved);
        }
        return "redirect:/deck/overview";
    }

    @GetMapping("/delete/{deckId}")
    private String deleteDeck(@PathVariable("deckId") Long deckId) {
        deckRepository.deleteById(deckId);
        return "redirect:/deck/overview";
    }

    @GetMapping("/update/{deckId}")
    private String updateDeck(@PathVariable Long deckId, Model datamodel) {
        Deck deck = deckRepository.findById(deckId).orElseThrow();
        datamodel.addAttribute("formDeck", deck);
        datamodel.addAttribute("allCards", cardRepository.findAll());
        return "updateDeckForm";
    }

    @PostMapping("/update/save")
    private String saveUpdatedDeck(@ModelAttribute("formDeck") Deck formDeck, BindingResult result) {
        if (result.hasErrors()) {
            return "updateDeckForm";
        }

        Deck deck = deckRepository.findById(formDeck.getDeckId()).orElseThrow();
        deck.getCards().addAll(formDeck.getCards());
        deckRepository.save(deck);

        return "redirect:/deck/overview";
    }
}
