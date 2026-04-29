package org.lessons.java.spring_pizzeria_crud.controllers;

import java.util.List;
import java.util.Optional;

import org.lessons.java.spring_pizzeria_crud.models.Pizza;
import org.lessons.java.spring_pizzeria_crud.repository.IngredientRepository;
import org.lessons.java.spring_pizzeria_crud.repository.PizzaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/pizze")
public class PizzaController {

    @Autowired
    private PizzaRepository pizzaRepository;

    @Autowired
    private IngredientRepository ingredientRepository;

    // INDEX 1
    @GetMapping
    public String index(Model model, @RequestParam(name = "keyword", required = false) String keyword) {
        List<Pizza> listaPizze;

        if (keyword != null && !keyword.isEmpty()) {
            listaPizze = pizzaRepository.findByNameContainingIgnoreCase(keyword);
        } else {
            listaPizze = pizzaRepository.findAll();
        }
        model.addAttribute("pizze", listaPizze);
        return "pizze/index";
    }

    // SHOW 2
    @GetMapping("/{id}")
    public String show(@PathVariable("id") Integer id, Model model) {

        Optional<Pizza> pizza = pizzaRepository.findById(id);
        if (pizza.isPresent()) {
            model.addAttribute("pizza", pizza.get());
            return "pizze/show";
        }
        return "redirect:/pizze";
    }

    // CREATE 3
    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("pizza", new Pizza());
        model.addAttribute("allIngredients", ingredientRepository.findAll());
        return "pizze/create";
    }

    // STORE 4
    @PostMapping("/store")
    public String store(@Valid @ModelAttribute("pizza") Pizza formPizza, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "pizza/create";
        }
        pizzaRepository.save(formPizza);
        return "redirect:/pizze";
    }

    // UPDATE 5
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Integer id, Model model) {

        Pizza pizza = pizzaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Pizza non trovata"));

        model.addAttribute("pizza", pizza);
        model.addAttribute("allIngredients", ingredientRepository.findAll());
        return "pizze/edit";
    }

    // UPDATE 6
    @PostMapping("/edit/{id}")
    public String update(@Valid @ModelAttribute("pizza") Pizza formPizza, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "pizze/edit";
        }
        pizzaRepository.save(formPizza);
        return "redirect:/pizze";
    }

    // DELETE
    @PostMapping("/delete/{id}")
    public String delete(@PathVariable("id") Integer id) {
        pizzaRepository.deleteById(id);
        return "redirect:/pizze";
    }

}
