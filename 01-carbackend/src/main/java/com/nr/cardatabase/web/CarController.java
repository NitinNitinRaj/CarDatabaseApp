package com.nr.cardatabase.web;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.nr.cardatabase.entities.Car;
import com.nr.cardatabase.representationalModels.CarRepresentationalModel;
import com.nr.cardatabase.representationalModels.OwnerRepresentationalModel;
import com.nr.cardatabase.service.CarService;
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
@RequestMapping(value = "/api/1.0/cars", produces = "application/hal+json")
@AllArgsConstructor
public class CarController {

  private final CarService carService;

  @GetMapping
  public ResponseEntity<CollectionModel<CarRepresentationalModel>> getCars() {
    List<CarRepresentationalModel> collection = carService
      .getAllCars()
      .stream()
      .map(CarRepresentationalModel::new)
      .collect(Collectors.toList());
    CollectionModel<CarRepresentationalModel> cars = CollectionModel.of(
      collection,
      linkTo(methodOn(CarController.class).getCars()).withSelfRel(),
      linkTo(methodOn(Controller.class).getSearchLinks()).withRel("search")
    );
    return new ResponseEntity<>(cars, HttpStatus.OK);
  }

  @GetMapping("/{id}")
  public ResponseEntity<CarRepresentationalModel> getCar(
    @PathVariable Long id
  ) {
    return new ResponseEntity<>(
      new CarRepresentationalModel(carService.getCar(id)),
      HttpStatus.OK
    );
  }

  @GetMapping("/{id}/owner")
  public ResponseEntity<OwnerRepresentationalModel> getOwnerOfCar(
    @PathVariable Long id
  ) {
    return new ResponseEntity<>(
      new OwnerRepresentationalModel(carService.getOwnerById(id)),
      HttpStatus.OK
    );
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<HttpStatus> deleteCar(@PathVariable Long id) {
    carService.deleteCar(id);
    return new ResponseEntity<HttpStatus>(HttpStatus.NO_CONTENT);
  }

  @PostMapping
  public ResponseEntity<CarRepresentationalModel> postCar(
    @Valid @RequestBody Car car
  ) {
    return new ResponseEntity<>(
      new CarRepresentationalModel(carService.saveCar(car)),
      HttpStatus.CREATED
    );
  }

  @PostMapping("/owner/{ownerId}")
  public ResponseEntity<CarRepresentationalModel> postCarWithOwner(
    @Valid @RequestBody Car car,
    @PathVariable Long ownerId
  ) {
    return new ResponseEntity<>(
      new CarRepresentationalModel(carService.saveCarWithOwner(car, ownerId)),
      HttpStatus.CREATED
    );
  }

  @PutMapping("/{id}")
  public ResponseEntity<CarRepresentationalModel> putCar(
    @Valid @RequestBody Car car,
    @PathVariable Long id
  ) {
    return new ResponseEntity<>(
      new CarRepresentationalModel(carService.updateCar(car, id)),
      HttpStatus.OK
    );
  }

  @PutMapping("/{id}/owner/{ownerId}")
  public ResponseEntity<CarRepresentationalModel> putCarWithOwner(
    @Valid @RequestBody Car car,
    @PathVariable Long id,
    @PathVariable Long ownerId
  ) {
    return new ResponseEntity<>(
      new CarRepresentationalModel(
        carService.updateCarWithOwner(car, id, ownerId)
      ),
      HttpStatus.OK
    );
  }

  @GetMapping("/owner/{ownerId}")
  public ResponseEntity<CollectionModel<CarRepresentationalModel>> getOwnersCar(
    @PathVariable Long ownerId
  ) {
    List<CarRepresentationalModel> collection = carService
      .getOwnersCar(ownerId)
      .stream()
      .map(CarRepresentationalModel::new)
      .collect(Collectors.toList());
    CollectionModel<CarRepresentationalModel> cars = CollectionModel.of(
      collection,
      linkTo(methodOn(CarController.class).getOwnersCar(null)).withSelfRel(),
      linkTo(methodOn(CarController.class).getOwnersCar(null))
        .withRel("owner's cars")
      /*, linkTo(methodOn(Controller.class).getSearchLinks()).withRel("search")*/
    );
    return new ResponseEntity<>(cars, HttpStatus.OK);
  }

  @DeleteMapping("/owner/{ownerId}")
  public ResponseEntity<HttpStatus> deleteOwnersCar(
    @PathVariable Long ownerId
  ) {
    carService.deleteOwnersCar(ownerId);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

  @GetMapping("/search/color/{color}")
  public ResponseEntity<CollectionModel<CarRepresentationalModel>> getCarByColor(
    @PathVariable String color
  ) {
    List<CarRepresentationalModel> collection = carService
      .getCarByColor(color)
      .stream()
      .map(CarRepresentationalModel::new)
      .collect(Collectors.toList());
    CollectionModel<CarRepresentationalModel> cars = CollectionModel.of(
      collection,
      linkTo(methodOn(CarController.class).getCarByColor(null)).withSelfRel(),
      linkTo(methodOn(CarController.class).getCarByColor(null))
        .withRel("searchByColor")
    );
    return new ResponseEntity<>(cars, HttpStatus.OK);
  }

  @GetMapping("/search/brand/{brand}")
  public ResponseEntity<CollectionModel<CarRepresentationalModel>> getCarByBrand(
    @PathVariable String brand
  ) {
    List<CarRepresentationalModel> collection = carService
      .getCarByBrand(brand)
      .stream()
      .map(CarRepresentationalModel::new)
      .collect(Collectors.toList());
    CollectionModel<CarRepresentationalModel> cars = CollectionModel.of(
      collection,
      linkTo(methodOn(CarController.class).getCarByColor(null)).withSelfRel(),
      linkTo(methodOn(CarController.class).getCarByColor(null))
        .withRel("searchByBrand")
    );
    return new ResponseEntity<>(cars, HttpStatus.OK);
  }
}
