package nl.miwnn.ch16.bas.tcglibrary.controller;

import nl.miwnn.ch16.bas.tcglibrary.model.Card;
import nl.miwnn.ch16.bas.tcglibrary.model.Expansion;
import nl.miwnn.ch16.bas.tcglibrary.repositories.CardRepository;
import nl.miwnn.ch16.bas.tcglibrary.repositories.ExpansionRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
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

        Expansion expansion = optionalExpansion.get();

        List<Card> allCards = cardRepository.findAll();
        List<Card> filteredCards = new ArrayList<>();
        for (Card allCard : allCards) {
            if (allCard.getExpansion().getExpansionId().equals(expansionId)) {
                filteredCards.add(allCard);
            }
        }
        allCards = filteredCards;
        List<Card> collectedCards = expansion.getCards();
        List<Card> availableCards = new ArrayList<>(allCards);
        availableCards.removeAll(collectedCards);

        datamodel.addAttribute("availableCards", availableCards);
        datamodel.addAttribute("collectedCards", collectedCards);
        datamodel.addAttribute("formExpansion", expansion);

        return "updateExpansionForm";
    }

    @PostMapping("/expansion/update/save")
    private String saveUpdatedExpansion(@ModelAttribute("formExpansion") Expansion formExpansion,
                                        @RequestParam(value = "deleteCardIds", required = false) Long[] deleteCardIds,
                                        BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "updateExpansionForm";
        }

        Expansion expansion = expansionRepository.findById(formExpansion.getExpansionId()).orElseThrow();

        // 1. Verwijder geselecteerde kaarten
        if (deleteCardIds != null) {
            for (Long cardId : deleteCardIds) {
                cardRepository.findById(cardId).ifPresent(card -> {
                    expansion.getCards().remove(card);
                    card.setExpansion(null);
                    cardRepository.save(card);
                });
            }
        }

        // 2. Voeg nieuwe kaarten toe (ook duplicaten toegestaan)
        if (formExpansion.getCards() != null) {
            for (Card card : formExpansion.getCards()) {
                // Altijd koppelen, zelfs als de kaart al voorkomt
                cardRepository.findById(card.getCardId()).ifPresent(c -> {
                    c.setExpansion(expansion);
                    cardRepository.save(c);
                    expansion.getCards().add(c);
                });
            }
        }

        // 3. Update count van toegevoegde kaarten
        expansion.setNumberOfAddedCards(expansion.getCards().size());

        expansionRepository.save(expansion);
        return "redirect:/expansion/overview";
    }

}
