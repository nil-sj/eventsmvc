package org.launchcode.codingevents.controllers;

import jakarta.validation.Valid;
import org.launchcode.codingevents.data.EventCategoryRepository;
import org.launchcode.codingevents.data.TagRepository;
import org.launchcode.codingevents.models.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("tags")
public class TagController {

    @Autowired
    private TagRepository tagRepository;

    @GetMapping
    public String displayTags(Model model) {
        model.addAttribute("tags", tagRepository.findAll());
        model.addAttribute("title", "Coding Events - Thymeleaf");
        model.addAttribute("subTitle", "List of All Tags");
        return "tags/index";
    }

    @GetMapping("create")
    public String displayCreateTagForm(Model model) {
        model.addAttribute("title", "Coding Events - Thymeleaf");
        model.addAttribute("subTitle", "Create A Tag");
        model.addAttribute("tag", new Tag());
        return "tags/create";
    }

    @PostMapping("create")
    public String processCreateTagForm(@ModelAttribute @Valid Tag tag, Errors errors, Model model) {
        if (errors.hasErrors()) {
            model.addAttribute("title", "Coding Events - Thymeleaf");
            model.addAttribute("subTitle", "Create A Tag - Correct the Error");
            model.addAttribute("tag", tag);
            return "tags/create";
        }

        tagRepository.save(tag);
        return "redirect:/tags";
    }
}
