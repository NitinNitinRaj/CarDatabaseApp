package com.nr.cardatabase;

import com.nr.cardatabase.entities.Car;
import com.nr.cardatabase.entities.Owner;
import com.nr.cardatabase.entities.User;
import com.nr.cardatabase.repository.CarRepository;
import com.nr.cardatabase.repository.OwnerRepository;
import com.nr.cardatabase.repository.UserRepository;
import java.util.List;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@AllArgsConstructor
@SpringBootApplication
public class CardatabaseApplication implements CommandLineRunner {

  private final CarRepository carRepository;
  private final OwnerRepository ownerRepository;
  private final UserRepository userRepository;

  private static final Logger LOGGER = LoggerFactory.getLogger(
    CardatabaseApplication.class
  );

  public static void main(String[] args) {
    SpringApplication.run(CardatabaseApplication.class, args);
    LOGGER.info("Spring Boot App has started");
  }

  @Override
  public void run(String... args) throws Exception {
    ownerRepository.deleteAll();
    carRepository.deleteAll();
    Owner owner1 = new Owner("John", "Johnson");
    Owner owner2 = new Owner("Mary", "Robinson");
    ownerRepository.saveAll(List.of(owner1, owner2));
    List<Car> cars = List.of(
      new Car("Ford", "Mustang", "Red", "ADF-1121", 2017, 59000, owner1),
      new Car("Nissan", "Leaf", "White", "SSJ-3002", 2014, 29000, owner2),
      new Car("Toyota", "Prius", "Silver", "KKO-0212", 2018, 39000, owner1),
      new Car("Tesla", "S", "Silver", "KSL-0487", 2019, 90000, owner2),
      new Car("Rivian", "Truck", "Green", "FAD-0472", 2020, 100000, owner1),
      new Car("Tesla", "Roadster", "White", "JKF-0212", 2022, 150000, owner2),
      new Car("Tata", "Nano", "Pink", "ZDF-0258", 2011, 2000, owner1)
    );
    carRepository.saveAll(cars);
    userRepository.save(
      new User(
        "nitin",
        "$2a$10$gvQyvGSQlz0Xg5jARpYEXOUeyhzftJT7neXe/LUJ4muKJybxxawsy"
      )
    );
  }

  @Bean
  public BCryptPasswordEncoder bCryptPasswordEncoder() {
    return new BCryptPasswordEncoder();
  }
}
