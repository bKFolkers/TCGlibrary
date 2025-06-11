package nl.miwnn.ch16.bas.tcglibrary.controller;

import nl.miwnn.ch16.bas.tcglibrary.model.Card;
import nl.miwnn.ch16.bas.tcglibrary.repositories.CardRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDateTime;

/**
 * @author Bas Folkers
 * Handle all requests related to trading cards
 */

@Controller
public class CardController {
    private final CardRepository cardRepository;

    public CardController(CardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }

    @GetMapping({"/", "/card/overview"})
    private String showCardOverview(Model datamodel) {
        datamodel.addAttribute("allCards", cardRepository.findAll());
        return "cardOverview";
    }

    @GetMapping("/card/new")
    private String showNewCardForm(Model datamodel) {
        datamodel.addAttribute("formCard", new Card());

        return "cardForm";
    }

    @PostMapping("/card/save")
    private String saveOrUpdateCard(@ModelAttribute("formCard") Card cardToBeSaved, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            System.err.println(bindingResult.getAllErrors());
        } else {
            cardRepository.save(cardToBeSaved);
        }

        return "redirect:/card/overview";
    }

    @GetMapping("/card/delete/{cardId}")
    private String deleteCard(@PathVariable("cardId") Long cardId) {
        cardRepository.deleteById(cardId);
        return "redirect:/card/overview";
    }
}
