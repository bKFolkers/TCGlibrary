package nl.miwnn.ch16.bas.tcglibrary.controller;

import nl.miwnn.ch16.bas.tcglibrary.repositories.CardRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

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

    @GetMapping("/")
    private String showCardOverview(Model datamodel) {
        datamodel.addAttribute("allCards", cardRepository.findAll());
        return "cardOverview";
    }
}
