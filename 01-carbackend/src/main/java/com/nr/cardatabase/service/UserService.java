package com.nr.cardatabase.service;

import com.nr.cardatabase.entities.User;

public interface UserService {
  User getUser(String username);
  User getUser(Long id);
  User saveUser(User user);
}
