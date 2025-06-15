package nl.miwnn.ch16.bas.tcglibrary.controller;

import nl.miwnn.ch16.bas.tcglibrary.model.Deck;
import nl.miwnn.ch16.bas.tcglibrary.model.Expansion;
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

    @PostMapping("/save")
    private String saveNewDeck(@ModelAttribute("formDeck") Deck deckToBeSaved, BindingResult bindingResult) {
        if (!bindingResult.hasErrors()) {
            deckRepository.save(deckToBeSaved);
        }
        return "redirect:/deck/overview";
    }

    @GetMapping("/delete/{deckId}")
    private String deleteCard(@PathVariable("deckId") Long deckId) {
        deckRepository.deleteById(deckId);
        return "redirect:/deck/overview";
    }

    @GetMapping("/update/{deckId}")
    private String updateDeck(@PathVariable String deckId, Model datamodel) {
        datamodel.addAttribute("formDeck", new Deck());
        datamodel.addAttribute("allDecks", deckRepository.findAll());
        datamodel.addAttribute("allCards", cardRepository.findAll());

        return "updateDeckForm";
    }

//    @PostMapping("/expansion/save")
//    private String saveOrUpdateExpansion(@ModelAttribute("formExpansion") Expansion expansionToBeSaved,
//                                         BindingResult bindingResult) {
//        if (bindingResult.hasErrors()) {
//            System.err.println(bindingResult.getAllErrors());
//        } else {
//            expansionRepository.save(expansionToBeSaved);
//        }
//
//        return "redirect:/expansion/overview";
//    }
}
