package nl.miwnn.ch16.bas.tcglibrary.controller;

import nl.miwnn.ch16.bas.tcglibrary.model.Card;
import nl.miwnn.ch16.bas.tcglibrary.model.Expansion;
import nl.miwnn.ch16.bas.tcglibrary.repositories.CardRepository;
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
    private final CardRepository cardRepository;

    public ExpansionController(ExpansionRepository expansionRepository, CardRepository cardRepository) {
        this.expansionRepository = expansionRepository;
        this.cardRepository = cardRepository;
    }

    @GetMapping({"/", "/expansion/overview"})
    private String showExpansionOverview(Model datamodel) {
        datamodel.addAttribute("allExpansions", expansionRepository.findAll());
        return "expansionOverview";
    }

    @GetMapping("/expansion/new")
    private String showNewExpansionForm(Model datamodel) {
        datamodel.addAttribute("formExpansion", new Expansion());
        return "newExpansionForm";
    }

    @GetMapping("/expansion/delete/{expansionId}")
    private String deleteExpansion(@PathVariable("expansionId") Long expansionId) {
        Optional<Expansion> optionalExpansion = expansionRepository.findById(expansionId);
        if (optionalExpansion.isPresent()) {
            Expansion expansion = optionalExpansion.get();

            // Alle kaarten loskoppelen
            for (Card card : expansion.getCards()) {
                card.setExpansion(null);
                cardRepository.save(card);
            }

            // Nu pas de expansion verwijderen
            expansionRepository.delete(expansion);
        }
        return "redirect:/expansion/overview";
    }

    @PostMapping("/expansion/save")
    private String saveNewExpansion(@ModelAttribute("formExpansion") Expansion expansionToBeSaved,
                                    BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            System.err.println(bindingResult.getAllErrors());
        } else {
            expansionRepository.save(expansionToBeSaved);
        }

        return "redirect:/expansion/overview";
    }

    @GetMapping("/expansion/update/{expansionId}")
    private String updateExpansion(@PathVariable Long expansionId, Model datamodel) {
        Optional<Expansion> optionalExpansion = expansionRepository.findById(expansionId);

        if (optionalExpansion.isEmpty()) {
            return "redirect:/expansion/overview";
        }

        datamodel.addAttribute("formExpansion", optionalExpansion.get());
        datamodel.addAttribute("allCards", cardRepository.findAll());

        return "updateExpansionForm";
    }

    @PostMapping("/expansion/update/save")
    private String saveUpdatedExpansion(@ModelAttribute("formExpansion") Expansion formExpansion,
                                        BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "updateExpansionForm";
        }

        Expansion expansion = expansionRepository.findById(formExpansion.getExpansionId()).orElseThrow();

        // Voeg nieuwe kaarten toe aan de bestaande lijst (zonder clear)
        expansion.getCards().addAll(formExpansion.getCards());

        // Optellen van het oude aantal toegevoegde kaarten met het nieuwe aantal kaarten uit het formulier
        int oldAmount = expansion.getNumberOfAddedCards() != null ? expansion.getNumberOfAddedCards() : 0;
        int newAmount = formExpansion.getCards() != null ? formExpansion.getCards().size() : 0;
        expansion.setNumberOfAddedCards(oldAmount + newAmount);

        // numberOfCards blijft ongewijzigd

        expansionRepository.save(expansion);

        return "redirect:/expansion/overview";
    }

}
