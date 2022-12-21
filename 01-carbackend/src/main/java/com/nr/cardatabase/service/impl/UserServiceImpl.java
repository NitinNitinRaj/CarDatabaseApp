package com.nr.cardatabase.service.impl;

import com.nr.cardatabase.entities.User;
import com.nr.cardatabase.repository.UserRepository;
import com.nr.cardatabase.service.UserService;
import java.util.Optional;
import javax.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

  private UserRepository userRepository;
  private BCryptPasswordEncoder bCryptPasswordEncoder;

  @Override
  public User getUser(String username) {
    return unwrap(userRepository.findByUsername(username), 404L);
  }

  @Override
  public User getUser(Long id) {
    return unwrap(userRepository.findById(id), id);
  }

  @Override
  public User saveUser(User user) {
    user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
    return userRepository.save(user);
  }

  public static User unwrap(Optional<User> user, Long id) {
    if (user.isPresent()) {
      return user.get();
    } else {
      throw new EntityNotFoundException(
        "User with id '" + id + "' is not present in our records"
      );
    }
  }
}
