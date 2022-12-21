package com.nr.cardatabase.web;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.nr.cardatabase.entities.Owner;
import com.nr.cardatabase.representationalModels.OwnerRepresentationalModel;
import com.nr.cardatabase.service.OwnerService;
import java.util.List;
import java.util.stream.Collector;
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
  public ResponseEntity<CollectionModel<OwnerRepresentationalModel>> getOwners() {
    List<OwnerRepresentationalModel> collection = ownerService
      .getAllOwners()
      .stream()
      .map(OwnerRepresentationalModel::new)
      .collect(Collectors.toList());
    CollectionModel<OwnerRepresentationalModel> owners = CollectionModel.of(
      collection,
      linkTo(methodOn(OwnerController.class).getOwners()).withSelfRel(),
      linkTo(methodOn(OwnerController.class).getOwners()).withRel("owners"),
      linkTo(methodOn(CarController.class).getOwnersCar(null))
        .withRel("owner's cars")
    );
    return new ResponseEntity<>(owners, HttpStatus.OK);
  }

  @GetMapping("/{id}")
  public ResponseEntity<OwnerRepresentationalModel> getOwner(
    @PathVariable Long id
  ) {
    return new ResponseEntity<>(
      new OwnerRepresentationalModel(ownerService.getOwner(id)),
      HttpStatus.OK
    );
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<HttpStatus> deleteCar(@PathVariable Long id) {
    ownerService.deleteOwner(id);
    return new ResponseEntity<HttpStatus>(HttpStatus.NO_CONTENT);
  }

  @PostMapping
  public ResponseEntity<OwnerRepresentationalModel> postCar(
    @Valid @RequestBody Owner owner
  ) {
    return new ResponseEntity<>(
      new OwnerRepresentationalModel(ownerService.saveOwner(owner)),
      HttpStatus.CREATED
    );
  }

  @PutMapping("/{id}")
  public ResponseEntity<OwnerRepresentationalModel> putCar(
    @Valid @RequestBody Owner owner,
    @PathVariable Long id
  ) {
    return new ResponseEntity<>(
      new OwnerRepresentationalModel(ownerService.updateOwner(owner, id)),
      HttpStatus.OK
    );
  }
}
