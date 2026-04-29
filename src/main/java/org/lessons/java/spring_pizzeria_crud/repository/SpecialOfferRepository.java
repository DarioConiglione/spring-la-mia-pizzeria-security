package org.lessons.java.spring_pizzeria_crud.repository;

import org.lessons.java.spring_pizzeria_crud.models.SpecialOffer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpecialOfferRepository extends JpaRepository<SpecialOffer, Integer> {

}