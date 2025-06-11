package nl.miwnn.ch16.bas.tcglibrary.controller;

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

    @GetMapping("/")
    private String showCardOverview(Model datamodel) {
        datamodel.addAttribute("now", LocalDateTime.now());
        return "cardOverview";
    }
}
