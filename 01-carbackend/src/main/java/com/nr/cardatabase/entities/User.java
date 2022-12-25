package com.nr.cardatabase.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "users")
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(nullable = false, updatable = false)
  private Long id;

  @NotBlank(message = "Username cannot be blank")
  @Column(updatable = false, unique = true)
  private String username;

  @NotBlank(message = "Password cannot be blank")
  private String password;

  public User(String username, String password) {
    this.username = username;
    this.password = password;
  }
}
