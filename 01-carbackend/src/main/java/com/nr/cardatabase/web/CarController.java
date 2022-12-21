package com.nr.cardatabase.web;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.nr.cardatabase.entities.Car;
import com.nr.cardatabase.entities.Owner;
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
  public ResponseEntity<CollectionModel<Car>> getCars() {
    List<Car> collection = carService
      .getAllCars()
      .stream()
      .map(car -> addLinks(car.getId(), car))
      .collect(Collectors.toList());
    CollectionModel<Car> cars = CollectionModel.of(
      collection,
      linkTo(methodOn(CarController.class).getCars()).withSelfRel(),
      linkTo(methodOn(Controller.class).getSearchLinks()).withRel("search")
    );
    return new ResponseEntity<>(cars, HttpStatus.OK);
  }

  @GetMapping("/{id}")
  public ResponseEntity<Car> getCar(@PathVariable Long id) {
    Car car = carService.getCar(id);
    return new ResponseEntity<>(addLinks(id, car), HttpStatus.OK);
  }

  @GetMapping("/{id}/owner")
  public ResponseEntity<Owner> getOwnerOfCar(@PathVariable Long id) {
    return new ResponseEntity<>(
      OwnerController.addLinks(id, carService.getOwnerById(id)),
      HttpStatus.OK
    );
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<HttpStatus> deleteCar(@PathVariable Long id) {
    carService.deleteCar(id);
    return new ResponseEntity<HttpStatus>(HttpStatus.NO_CONTENT);
  }

  @PostMapping
  public ResponseEntity<Car> postCar(@Valid @RequestBody Car car) {
    Car savedCar = carService.saveCar(car);
    return new ResponseEntity<>(
      addLinks(savedCar.getId(), savedCar),
      HttpStatus.CREATED
    );
  }

  @PostMapping("/owner/{ownerId}")
  public ResponseEntity<Car> postCarWithOwner(
    @Valid @RequestBody Car car,
    @PathVariable Long ownerId
  ) {
    Car savedCar = carService.saveCarWithOwner(car, ownerId);
    return new ResponseEntity<>(
      addLinks(savedCar.getId(), savedCar),
      HttpStatus.CREATED
    );
  }

  @PutMapping("/{id}")
  public ResponseEntity<Car> putCar(
    @Valid @RequestBody Car car,
    @PathVariable Long id
  ) {
    Car savedCar = carService.updateCar(car, id);
    return new ResponseEntity<>(
      addLinks(savedCar.getId(), savedCar),
      HttpStatus.OK
    );
  }

  @PutMapping("/{id}/owner/{ownerId}")
  public ResponseEntity<Car> putCarWithOwner(
    @Valid @RequestBody Car car,
    @PathVariable Long id,
    @PathVariable Long ownerId
  ) {
    Car savedCar = carService.updateCarWithOwner(car, id, ownerId);
    return new ResponseEntity<>(
      addLinks(savedCar.getId(), savedCar),
      HttpStatus.OK
    );
  }

  @GetMapping("/owner/{ownerId}")
  public ResponseEntity<CollectionModel<Car>> getOwnersCar(
    @PathVariable Long ownerId
  ) {
    List<Car> collection = carService
      .getOwnersCar(ownerId)
      .stream()
      .map(car -> addLinks(car.getId(), car))
      .collect(Collectors.toList());
    CollectionModel<Car> cars = CollectionModel.of(
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
  public ResponseEntity<CollectionModel<Car>> getCarByColor(
    @PathVariable String color
  ) {
    List<Car> collection = carService
      .getCarByColor(color)
      .stream()
      .map(car -> addLinks(car.getId(), car))
      .collect(Collectors.toList());
    CollectionModel<Car> cars = CollectionModel.of(
      collection,
      linkTo(methodOn(CarController.class).getCarByColor(null)).withSelfRel(),
      linkTo(methodOn(CarController.class).getCarByColor(null))
        .withRel("searchByColor")
    );
    return new ResponseEntity<>(cars, HttpStatus.OK);
  }

  @GetMapping("/search/brand/{brand}")
  public ResponseEntity<CollectionModel<Car>> getCarByBrand(
    @PathVariable String brand
  ) {
    List<Car> collection = carService
      .getCarByBrand(brand)
      .stream()
      .map(car -> addLinks(car.getId(), car))
      .collect(Collectors.toList());
    CollectionModel<Car> cars = CollectionModel.of(
      collection,
      linkTo(methodOn(CarController.class).getCarByColor(null)).withSelfRel(),
      linkTo(methodOn(CarController.class).getCarByColor(null))
        .withRel("searchByBrand")
    );
    return new ResponseEntity<>(cars, HttpStatus.OK);
  }

  private Car addLinks(Long id, Car car) {
    car.add(linkTo(methodOn(CarController.class).getCar(id)).withSelfRel());
    car.add(linkTo(methodOn(CarController.class).getCar(id)).withRel("car"));
    car.add(
      linkTo(methodOn(CarController.class).getOwnerOfCar(id)).withRel("owner")
    );
    return car;
  }
}
