package org.lessons.java.spring_pizzeria_crud.controllers.api;

import java.util.List;
import java.util.Optional;

import org.lessons.java.spring_pizzeria_crud.models.Pizza;
import org.lessons.java.spring_pizzeria_crud.repository.PizzaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@CrossOrigin
@RequestMapping("/api/pizze")
public class PizzaRestController {

    @Autowired
    private PizzaRepository pizzaRepository;

    // LISTA
    @GetMapping
    public List<Pizza> index(@RequestParam(required = false) String name) {
        if (name != null && !name.isBlank()) {
            return pizzaRepository.findByNameContainingIgnoreCase(name);
        }
        return pizzaRepository.findAll();
    }

    // DETTAGLIO PIZZA
    @GetMapping("/{id}")
    public ResponseEntity<Pizza> show(@PathVariable Integer id) {
        Optional<Pizza> res = pizzaRepository.findById(id);
        if (res.isPresent()) {
            return new ResponseEntity<>(res.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // 404 se non esiste
        }
    }

    // CREATE
    @PostMapping
    public Pizza store(@RequestBody Pizza pizza) {
        return pizzaRepository.save(pizza); // Salva e restituisce la pizza con l'ID generato
    }

    // UPDATE
    @PutMapping("/{id}")
    public Pizza update(@PathVariable Integer id, @RequestBody Pizza pizza) {
        pizza.setId(id); // Assicura che stiamo aggiornando l'ID corretto
        return pizzaRepository.save(pizza);
    }

    // DELETE
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        pizzaRepository.deleteById(id);
    }

}
