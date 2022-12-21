package com.nr.cardatabase.repositoryTest.restTest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.nr.cardatabase.entities.User;
import com.nr.cardatabase.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class CarRestTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private BCryptPasswordEncoder bCryptPasswordEncoder;

  @Before
  public void setup() {
    userRepository.save(
      new User("nitin", bCryptPasswordEncoder.encode("pass"))
    );
  }

  @Test
  public void testAuthentication() throws Exception {
    this.mockMvc.perform(
        post("/authenticate")
          .content("{\"username\":\"nitin\", \"password\":\"pass\"}")
      )
      .andDo(print())
      .andExpect(status().isOk());
    this.mockMvc.perform(
        post("/authenticate")
          .content("{\"username\":\"nitin\", \"password\":\"passd\"}")
      )
      .andDo(print())
      .andExpect(status().isUnauthorized());
  }
}
