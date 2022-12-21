package com.nr.cardatabase.web;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.nr.cardatabase.entities.Owner;
import com.nr.cardatabase.service.OwnerService;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/1.0/owners", produces = "application/hal+json")
@AllArgsConstructor
public class OwnerController {

  private final OwnerService ownerService;

  @GetMapping
  public ResponseEntity<CollectionModel<Owner>> getOwners() {
    List<Owner> collection = ownerService
      .getAllOwners()
      .stream()
      .map(owner -> addLinks(owner.getId(), owner))
      .collect(Collectors.toList());
    CollectionModel<Owner> owners = CollectionModel.of(
      collection,
      linkTo(methodOn(OwnerController.class).getOwners()).withSelfRel(),
      linkTo(methodOn(OwnerController.class).getOwners()).withRel("owners"),
      linkTo(methodOn(CarController.class).getOwnersCar(null))
        .withRel("owner's cars")
    );
    return new ResponseEntity<>(owners, HttpStatus.OK);
  }

  @GetMapping("/{id}")
  public ResponseEntity<Owner> getOwner(@PathVariable Long id) {
    return new ResponseEntity<>(
      addLinks(id, ownerService.getOwner(id)),
      HttpStatus.OK
    );
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<HttpStatus> deleteCar(@PathVariable Long id) {
    ownerService.deleteOwner(id);
    return new ResponseEntity<HttpStatus>(HttpStatus.NO_CONTENT);
  }

  @PostMapping
  public ResponseEntity<Owner> postCar(@Valid @RequestBody Owner owner) {
    Owner savedOwner = ownerService.saveOwner(owner);
    return new ResponseEntity<>(
      addLinks(savedOwner.getId(), savedOwner),
      HttpStatus.CREATED
    );
  }

  @PutMapping("/{id}")
  public ResponseEntity<Owner> putCar(
    @Valid @RequestBody Owner owner,
    @PathVariable Long id
  ) {
    Owner savedOwner = ownerService.updateOwner(owner, id);
    return new ResponseEntity<>(
      addLinks(savedOwner.getId(), savedOwner),
      HttpStatus.OK
    );
  }

  public static Owner addLinks(Long id, Owner owner) {
    owner.add(
      linkTo(methodOn(OwnerController.class).getOwner(id)).withSelfRel()
    );
    owner.add(
      linkTo(methodOn(OwnerController.class).getOwner(id)).withRel("owner")
    );
    owner.add(
      linkTo(methodOn(CarController.class).getOwnersCar(id)).withRel("cars")
    );
    return owner;
  }
}
