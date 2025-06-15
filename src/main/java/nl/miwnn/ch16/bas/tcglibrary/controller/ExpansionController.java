package nl.miwnn.ch16.bas.tcglibrary.controller;

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

    @GetMapping("/expansion/update/{expansionId}")
    private String updateExpansion(@PathVariable("expansionId") Long expansionId, Model datamodel) {
        Expansion expansion = expansionRepository.findById(expansionId).orElseThrow();
        datamodel.addAttribute("formExpansion", expansion);
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
        expansion.getCards().addAll(formExpansion.getCards());
        expansionRepository.save(expansion);

        return "redirect:/expansion/overview";
    }

}
