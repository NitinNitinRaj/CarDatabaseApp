package com.nr.cardatabase.representationalModels;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import com.nr.cardatabase.entities.Car;
import com.nr.cardatabase.web.CarController;
import lombok.Getter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

@Getter
@Relation(collectionRelation = "cars")
public class CarRepresentationalModel
  extends RepresentationModel<CarRepresentationalModel> {

  private final Car car;

  public CarRepresentationalModel(Car car) {
    this.car = car;
    final Long id = car.getId();
    add(linkTo(methodOn(CarController.class).getCar(id)).withSelfRel());
    add(linkTo(methodOn(CarController.class).getCar(id)).withRel("car"));
    add(
      linkTo(methodOn(CarController.class).getOwnerOfCar(id)).withRel("owner")
    );
  }
}
