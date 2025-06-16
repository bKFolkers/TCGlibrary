package nl.miwnn.ch16.bas.tcglibrary.controller;

import nl.miwnn.ch16.bas.tcglibrary.model.Card;
import nl.miwnn.ch16.bas.tcglibrary.model.Deck;
import nl.miwnn.ch16.bas.tcglibrary.repositories.CardRepository;
import nl.miwnn.ch16.bas.tcglibrary.repositories.DeckRepository;
import org.hibernate.ObjectNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * @author Bas Folkers
 * Handles all requests related to decks consisting of cards
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
        Optional<Deck> optionalDeck = deckRepository.findById(deckId);

        if (optionalDeck.isEmpty()) {
            return "redirect:/deck/overview";
        }

        datamodel.addAttribute("formDeck", optionalDeck.get());
        datamodel.addAttribute("allCards", cardRepository.findAll());
        return "updateDeckForm";
    }

    @PostMapping("/update/save")
    private String saveUpdatedDeck(@ModelAttribute("formDeck") Deck formDeck, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "updateDeckForm";
        }

        Deck deck = deckRepository.findById(formDeck.getDeckId()).orElseThrow();
        deck.getCards().addAll(formDeck.getCards());
        deckRepository.save(deck);

        return "redirect:/deck/overview";
    }
}
