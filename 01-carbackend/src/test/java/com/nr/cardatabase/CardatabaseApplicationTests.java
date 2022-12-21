package com.nr.cardatabase;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

import com.nr.cardatabase.web.CarController;
import com.nr.cardatabase.web.OwnerController;
import com.nr.cardatabase.web.UserController;
import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
class CardatabaseApplicationTests {

  @Autowired
  private CarController carController;

  @Autowired
  private OwnerController ownerController;

  @Autowired
  private UserController userController;

  @Test
  void contextLoads() {
    assertNotEquals(null, carController);
    assertNotEquals(null, ownerController);
    assertNotEquals(null, userController);
  }
}
