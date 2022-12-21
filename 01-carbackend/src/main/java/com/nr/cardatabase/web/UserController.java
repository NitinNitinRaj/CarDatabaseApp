package com.nr.cardatabase.web;

import com.nr.cardatabase.entities.User;
import com.nr.cardatabase.service.UserService;
import javax.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/1.0/users", produces = "application/hal+json")
@AllArgsConstructor
public class UserController {

  private UserService userService;

  @GetMapping("/{id}")
  public ResponseEntity<String> findById(@PathVariable Long id) {
    return new ResponseEntity<>(
      userService.getUser(id).getUsername(),
      HttpStatus.OK
    );
  }

  @PostMapping("/register")
  public ResponseEntity<HttpStatus> postUser(@Valid @RequestBody User user) {
    userService.saveUser(user);
    return new ResponseEntity<>(HttpStatus.CREATED);
  }
}
