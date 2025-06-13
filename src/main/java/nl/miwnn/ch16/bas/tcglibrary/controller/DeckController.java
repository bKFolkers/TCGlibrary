package nl.miwnn.ch16.bas.tcglibrary.controller;

import nl.miwnn.ch16.bas.tcglibrary.model.Card;
import nl.miwnn.ch16.bas.tcglibrary.model.Deck;
import nl.miwnn.ch16.bas.tcglibrary.repositories.CardRepository;
import nl.miwnn.ch16.bas.tcglibrary.repositories.DeckRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/add")
    private String addNewCardToDeck(Model datamodel) {
        datamodel.addAttribute("formDeck", new Deck());
        datamodel.addAttribute("allDecks", deckRepository.findAll());
        datamodel.addAttribute("allCards", cardRepository.findAll());

        return "addCardForm";
    }

    @PostMapping("/save")
    private String saveCardsToExistingDeck(@ModelAttribute("formDeck") Deck deckForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "addCardForm";
        }

        Deck existingDeck = deckRepository.findById(deckForm.getDeckId()).orElse(null);

        if (existingDeck != null) {
            existingDeck.getCards().addAll(deckForm.getCards());
            deckRepository.save(existingDeck);
        }

        return "redirect:/deck/overview";
    }

    @GetMapping("/delete/{deckId}")
    private String deleteCard(@PathVariable("deckId") Long deckId) {
        deckRepository.deleteById(deckId);
        return "redirect:/deck/overview";
    }
}
