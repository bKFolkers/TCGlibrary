package nl.miwnn.ch16.bas.tcglibrary.controller;

import nl.miwnn.ch16.bas.tcglibrary.model.Card;
import nl.miwnn.ch16.bas.tcglibrary.model.Expansion;
import nl.miwnn.ch16.bas.tcglibrary.repositories.CardRepository;
import nl.miwnn.ch16.bas.tcglibrary.repositories.ExpansionRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDateTime;
import java.util.Optional;

/**
 * @author Bas Folkers
 * Handle all requests related to trading cards
 */

@Controller
public class CardController {
    private final CardRepository cardRepository;
    private final ExpansionRepository expansionRepository;

    public CardController(CardRepository cardRepository, ExpansionRepository expansionRepository) {
        this.cardRepository = cardRepository;
        this.expansionRepository = expansionRepository;
    }

    @GetMapping("/card/new/{expansionId}")
    private String createNewCard(@PathVariable("expansionId") Long expansionId) {
        Optional<Expansion> optionalExpansion = expansionRepository.findById(expansionId);

        if (optionalExpansion.isPresent()) {
            Card card = new Card(optionalExpansion.get());
            cardRepository.save(card);
        }
        return "redirect:/card/new";
    }

    @GetMapping({"/card/overview"})
    private String showCardOverview(Model datamodel) {
        datamodel.addAttribute("allCards", cardRepository.findAll());
        return "cardOverview";
    }

    @GetMapping("/card/new")
    private String showNewCardForm(Model datamodel) {
        datamodel.addAttribute("formCard", new Card());
        datamodel.addAttribute("allExpansions", expansionRepository.findAll());
        return "cardForm";
    }

    @PostMapping("/card/save")
    private String saveOrUpdateCard(@ModelAttribute("formCard") Card cardToBeSaved, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            System.err.println(bindingResult.getAllErrors());
        } else {
            cardRepository.save(cardToBeSaved);
        }

        return "redirect:/";
    }

    @GetMapping("/card/delete/{cardId}")
    private String deleteCard(@PathVariable("cardId") Long cardId) {
        cardRepository.deleteById(cardId);
        return "redirect:/card/overview";
    }
}
