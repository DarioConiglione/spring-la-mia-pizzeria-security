package org.lessons.java.spring_pizzeria_crud.models;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.annotation.Generated;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "ingredients")
public class Ingredient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "Gli ingredienti devono avere un nome")
    private String name;

    @ManyToMany(mappedBy = "ingredients")
    @JsonIgnore
    private List<Pizza> pizze;

    public Ingredient() {
    }

    // Getter
    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<Pizza> getPizze() {
        return pizze;
    }

    // Setter
    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPizze(List<Pizza> pizze) {
        this.pizze = pizze;
    }

}
