package com.nr.cardatabase.service.impl;

import com.nr.cardatabase.entities.Car;
import com.nr.cardatabase.entities.Owner;
import com.nr.cardatabase.exception.EntityNotFoundExceptionHandler;
import com.nr.cardatabase.repository.CarRepository;
import com.nr.cardatabase.repository.OwnerRepository;
import com.nr.cardatabase.service.CarService;
import java.util.List;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CarServiceImpl implements CarService {

  private final CarRepository carRepository;
  private final OwnerRepository ownerRepository;

  @Override
  public List<Car> getAllCars() {
    return (List<Car>) carRepository.findAll();
  }

  @Override
  public Car getCar(Long id) {
    return unWarp(id, carRepository.findById(id));
  }

  @Override
  public Owner getOwnerById(Long id) {
    Car car = unWarp(id, carRepository.findById(id));
    return car.getOwner();
  }

  @Override
  public void deleteCar(Long id) {
    carRepository.deleteById(id);
  }

  @Override
  public Car saveCar(Car car) {
    return carRepository.save(car);
  }

  @Override
  public Car saveCarWithOwner(Car car, Long ownerId) {
    Owner owner = OwnerServiceImpl.unWarp(
      ownerId,
      ownerRepository.findById(ownerId)
    );
    car.setOwner(owner);
    return saveCar(car);
  }

  @Override
  public Car updateCar(Car car, Long id) {
    Car carDb = unWarp(id, carRepository.findById(id));
    carDb.setBrand(car.getBrand());
    carDb.setColor(car.getColor());
    carDb.setModel(car.getModel());
    carDb.setOwner(car.getOwner());
    carDb.setPrice(car.getPrice());
    carDb.setRegisterNumber(car.getRegisterNumber());
    carDb.setYear(car.getYear());
    return carRepository.save(carDb);
  }

  @Override
  public Car updateCarWithOwner(Car car, Long id, Long ownerId) {
    Car carDb = unWarp(id, carRepository.findById(id));
    Owner owner = OwnerServiceImpl.unWarp(
      ownerId,
      ownerRepository.findById(ownerId)
    );
    carDb.setBrand(car.getBrand());
    carDb.setColor(car.getColor());
    carDb.setModel(car.getModel());
    carDb.setOwner(owner);
    carDb.setPrice(car.getPrice());
    carDb.setRegisterNumber(car.getRegisterNumber());
    carDb.setYear(car.getYear());
    return carRepository.save(carDb);
  }

  @Override
  public List<Car> getOwnersCar(Long ownerId) {
    return carRepository.findByOwnerId(ownerId);
  }

  @Override
  public void deleteOwnersCar(Long ownerId) {
    carRepository.deleteByOwnerId(ownerId);
  }

  @Override
  public List<Car> getCarByColor(String color) {
    return carRepository.findByColor(color);
  }

  @Override
  public List<Car> getCarByBrand(String brand) {
    return carRepository.findByBrand(brand);
  }

  public static Car unWarp(Long id, Optional<Car> car) {
    if (car.isPresent()) {
      return car.get();
    } else {
      throw new EntityNotFoundExceptionHandler(id.toString(), Car.class);
    }
  }
}
