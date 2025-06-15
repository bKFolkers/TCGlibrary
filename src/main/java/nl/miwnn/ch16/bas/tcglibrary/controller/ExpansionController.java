package nl.miwnn.ch16.bas.tcglibrary.controller;

import nl.miwnn.ch16.bas.tcglibrary.model.Card;
import nl.miwnn.ch16.bas.tcglibrary.model.Deck;
import nl.miwnn.ch16.bas.tcglibrary.model.Expansion;
import nl.miwnn.ch16.bas.tcglibrary.repositories.ExpansionRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/**
 * @author Bas Folkers
 * Handle all requests related to card expansions
 */

@Controller
public class ExpansionController {
    private final ExpansionRepository expansionRepository;

    public ExpansionController(ExpansionRepository expansionRepository) {
        this.expansionRepository = expansionRepository;
    }

    @GetMapping({"/", "/expansion/overview"})
    private String showExpansionOverview(Model datamodel) {
        datamodel.addAttribute("allexpansions", expansionRepository.findAll());
        return "expansionOverview";
    }

    @GetMapping("/expansion/new")
    private String showNewExpansionForm(Model datamodel) {
        datamodel.addAttribute("formExpansion", new Expansion());
        return "expansionForm";
    }

    @GetMapping("/expansion/delete/{expansionId}")
    private String deleteExpansion(@PathVariable("expansionId") Long expansionId) {
        expansionRepository.deleteById(expansionId);
        return "redirect:/expansion/overview";
    }

    @PostMapping("/expansion/save")
    private String saveOrUpdateExpansion(@ModelAttribute("formExpansion") Expansion expansionToBeSaved,
                                         BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            System.err.println(bindingResult.getAllErrors());
        } else {
            expansionRepository.save(expansionToBeSaved);
        }

        return "redirect:/expansion/overview";
    }

//    @GetMapping("/expansion/update/{expansionId}")
//    private String updateExpansion(@PathVariable("expansionId") Long expansionId, Model datamodel) {
//        Optional<Expansion> optionalExpansion = expansionRepository.findById(expansionId);
//    }

//    @GetMapping("/new/{expansionId}")
//    private String createNewCard(@PathVariable("expansionId") Long expansionId) {
//        Optional<Expansion> optionalExpansion = expansionRepository.findById(expansionId);
//
//        if (optionalExpansion.isPresent()) {
//            Card card = new Card(optionalExpansion.get());
//            cardRepository.save(card);
//        }
//        return "redirect:/card/new";
//    }

//    @GetMapping("/update/{deckId}")
//    private String updateDeck(@PathVariable Long deckId, Model datamodel) {
//        Deck deck = deckRepository.findById(deckId).orElseThrow();
//        datamodel.addAttribute("formDeck", deck);
//        datamodel.addAttribute("allCards", cardRepository.findAll());
//        return "updateDeckForm";
//    }

//    @PostMapping("/update/save")
//    private String saveUpdatedDeck(@ModelAttribute("formDeck") Deck formDeck, BindingResult result) {
//        if (result.hasErrors()) {
//            return "updateDeckForm";
//        }
//
//        Deck deck = deckRepository.findById(formDeck.getDeckId()).orElseThrow();
//        deck.getCards().addAll(formDeck.getCards());
//        deckRepository.save(deck);
//
//        return "redirect:/deck/overview";
//    }

}
