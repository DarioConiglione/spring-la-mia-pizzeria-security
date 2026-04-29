package org.lessons.java.spring_pizzeria_crud.controllers;

import org.lessons.java.spring_pizzeria_crud.models.Pizza;
import org.lessons.java.spring_pizzeria_crud.models.SpecialOffer;
import org.lessons.java.spring_pizzeria_crud.repository.PizzaRepository;
import org.lessons.java.spring_pizzeria_crud.repository.SpecialOfferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/offers")
public class SpecialOfferController {

    @Autowired
    private SpecialOfferRepository offerRepository;

    @Autowired
    private PizzaRepository pizzaRepository;

    @GetMapping("/create")
    public String create(@RequestParam("pizzaId") Integer pizzaId, Model model) {
        SpecialOffer offer = new SpecialOffer();

        Pizza pizza = pizzaRepository.findById(pizzaId).get();
        offer.setPizza(pizza);

        model.addAttribute("specialOffer", offer);
        return "offers/form";
    }

    @PostMapping("/save")
    public String save(@Valid @ModelAttribute("specialOffer") SpecialOffer formOffer, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "offers/form";
        }
        offerRepository.save(formOffer);
        return "redirect:/pizze/" + formOffer.getPizza().getId();
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Integer id, Model model) {
        SpecialOffer offer = offerRepository.findById(id).get();
        model.addAttribute("specialOffer", offer);
        return "offers/form";
    }
}