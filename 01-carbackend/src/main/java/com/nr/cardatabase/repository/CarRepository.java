package com.nr.cardatabase.repository;

import com.nr.cardatabase.entities.Car;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.data.repository.CrudRepository;

public interface CarRepository extends CrudRepository<Car, Long> {
  List<Car> findByOwnerId(Long ownerId);

  @Transactional
  void deleteByOwnerId(Long ownerId);

  List<Car> findByColor(String color);
  List<Car> findByBrand(String brand);
}
