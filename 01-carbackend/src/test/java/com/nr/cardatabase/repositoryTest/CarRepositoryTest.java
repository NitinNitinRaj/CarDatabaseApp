package com.nr.cardatabase.repositoryTest;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.nr.cardatabase.entities.Car;
import com.nr.cardatabase.repository.CarRepository;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
public class CarRepositoryTest {

  @Autowired
  private TestEntityManager entityManager;

  @Autowired
  private CarRepository carRepository;

  @Test
  public void saveCarTest() {
    Car car = new Car(
      "Tesla",
      "Model X",
      "White",
      "ABC-1234",
      2017,
      86000,
      null
    );
    entityManager.persistAndFlush(car);
    assertNotEquals(null, car.getId());
  }

  @Test
  public void deleteCarTest() {
    entityManager.persistAndFlush(
      new Car("Tesla", "Model X", "White", "ABC-1234", 2017, 86000, null)
    );
    entityManager.persistAndFlush(
      new Car("Mini", "Cooper", "Yellow", "BWS-3007", 2015, 24500, null)
    );
    carRepository.deleteAll();
    assertTrue(((List<Car>) carRepository.findAll()).isEmpty());
  }
}
