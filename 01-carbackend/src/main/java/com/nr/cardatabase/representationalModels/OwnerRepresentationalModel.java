package com.nr.cardatabase.representationalModels;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import com.nr.cardatabase.entities.Owner;
import com.nr.cardatabase.web.CarController;
import com.nr.cardatabase.web.OwnerController;
import lombok.Getter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

@Getter
@Relation(collectionRelation = "owners")
public class OwnerRepresentationalModel
  extends RepresentationModel<OwnerRepresentationalModel> {

  private final Owner owner;

  public OwnerRepresentationalModel(Owner owner) {
    this.owner = owner;
    final Long id = owner.getId();
    add(linkTo(methodOn(OwnerController.class).getOwner(id)).withSelfRel());
    add(linkTo(methodOn(OwnerController.class).getOwner(id)).withRel("owner"));
    add(linkTo(methodOn(CarController.class).getOwnersCar(id)).withRel("cars"));
  }
}
