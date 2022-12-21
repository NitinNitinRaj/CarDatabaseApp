package com.nr.cardatabase.web;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import java.util.Arrays;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/1.0", produces = "application/hal+json")
public class Controller {

  @GetMapping
  public ResponseEntity<CollectionModel<Link>> getLinks() {
    CollectionModel<Link> collectionModel = CollectionModel.of(
      Arrays.asList(),
      linkTo(CarController.class).withRel("cars"),
      linkTo(OwnerController.class).withRel("owners")
    );
    return new ResponseEntity<>(collectionModel, HttpStatus.OK);
  }

  @GetMapping("cars/search")
  public ResponseEntity<CollectionModel<Link>> getSearchLinks() {
    CollectionModel<Link> collectionModel = CollectionModel.of(
      Arrays.asList(),
      linkTo(methodOn(Controller.class).getSearchLinks()).withSelfRel(),
      linkTo(methodOn(CarController.class).getCarByBrand(null))
        .withRel("searchByBrand"),
      linkTo(methodOn(CarController.class).getCarByColor(null))
        .withRel("searchByColor")
    );
    return new ResponseEntity<>(collectionModel, HttpStatus.OK);
  }
}
