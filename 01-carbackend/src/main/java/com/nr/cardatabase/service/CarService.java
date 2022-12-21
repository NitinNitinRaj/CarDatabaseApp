package com.nr.cardatabase.service;

import com.nr.cardatabase.entities.Car;
import com.nr.cardatabase.entities.Owner;
import java.util.List;

public interface CarService {
  List<Car> getAllCars();
  Car getCar(Long id);
  Owner getOwnerById(Long id);
  void deleteCar(Long id);
  Car saveCar(Car car);
  Car saveCarWithOwner(Car car, Long ownerId);
  Car updateCar(Car car, Long id);
  Car updateCarWithOwner(Car car, Long id, Long ownerId);
  List<Car> getOwnersCar(Long ownerId);
  void deleteOwnersCar(Long ownerId);
  List<Car> getCarByColor(String color);
  List<Car> getCarByBrand(String brand);
}
 