package org.lessons.java.spring_pizzeria_crud.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

import org.lessons.java.spring_pizzeria_crud.models.Pizza;

public interface PizzaRepository extends JpaRepository<Pizza, Integer> {

    List<Pizza> findByNameContainingIgnoreCase(String name);

}