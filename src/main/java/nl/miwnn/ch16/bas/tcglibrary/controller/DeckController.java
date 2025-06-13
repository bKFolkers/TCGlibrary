package nl.miwnn.ch16.bas.tcglibrary.controller;

import nl.miwnn.ch16.bas.tcglibrary.model.Deck;
import nl.miwnn.ch16.bas.tcglibrary.repositories.DeckRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Bas Folkers
 * Handles all requests related to decks of cards
 */

@Controller
@RequestMapping("/deck")
public class DeckController {
    private final DeckRepository deckRepository;

    public DeckController(DeckRepository deckRepository) {
        this.deckRepository = deckRepository;
    }

    @GetMapping("/overview")
    private String showDeckOverview(Model datamodel) {
        datamodel.addAttribute("allDecks", deckRepository.findAll());
        datamodel.addAttribute("formDeck", new Deck());

        return "deckOverview";
    }

    @PostMapping("/save")
    private String saveOrUpdateDeck(@ModelAttribute("formDeck") Deck deckToBeSaved, BindingResult bindingResult) {
        if (!bindingResult.hasErrors()){
            deckRepository.save(deckToBeSaved);
        }
        return "redirect:/deck/overview";
    }
}
