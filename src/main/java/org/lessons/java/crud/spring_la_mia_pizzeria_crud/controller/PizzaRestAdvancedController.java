package org.lessons.java.crud.spring_la_mia_pizzeria_crud.controller;

import java.util.List;
import java.util.Optional;

import org.lessons.java.crud.spring_la_mia_pizzeria_crud.model.Pizza;
import org.lessons.java.crud.spring_la_mia_pizzeria_crud.service.PizzaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/pizzas")
public class PizzaRestAdvancedController {

  @Autowired
  private PizzaService pizzaService;

  @GetMapping
  public List<Pizza> index() {

    List<Pizza> pizzas = pizzaService.findAll();
    return pizzas;
  }

  @GetMapping("/search")
  public List<Pizza> index(@Valid @RequestParam String query) {

    return pizzaService.findByName(query);
  }

  @GetMapping("/{id}")
  public ResponseEntity<Pizza> show(@Valid @PathVariable Integer id) {

    Optional<Pizza> pizzaAttempt = pizzaService.findById(id);

    if (pizzaAttempt.isEmpty()) {
      return new ResponseEntity<Pizza>(HttpStatus.NOT_FOUND);
    }

    return new ResponseEntity<Pizza>(pizzaAttempt.get(), HttpStatus.OK);
  }

  @PostMapping
  public ResponseEntity<Pizza> store(@Valid @RequestBody Pizza pizza) {

    return new ResponseEntity<Pizza>(pizzaService.create(pizza), HttpStatus.OK);
  }

  @PutMapping("/{id}")
  public ResponseEntity<Pizza> update(@Valid @RequestBody Pizza pizza, @Valid @PathVariable Integer id) {

    if (pizzaService.findById(id).isEmpty()) {
      return new ResponseEntity<Pizza>(HttpStatus.NOT_FOUND);
    }

    pizza.setId(id);
    return new ResponseEntity<Pizza>(pizzaService.update(pizza), HttpStatus.OK);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Pizza> delete(@Valid @PathVariable Integer id) {

    if (pizzaService.findById(id).isEmpty()) {
      return new ResponseEntity<Pizza>(HttpStatus.NOT_FOUND);
    }

    pizzaService.deleteById(id);
    return new ResponseEntity<Pizza>(HttpStatus.OK);
  }
}
