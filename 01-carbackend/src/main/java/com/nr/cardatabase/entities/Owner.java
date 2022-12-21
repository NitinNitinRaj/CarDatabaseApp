package com.nr.cardatabase.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class Owner {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotBlank(message = "First Name cannot be blank")
  @Column(unique = true)
  private String firstName;

  @NotBlank(message = "Last Name cannot be blank")
  @Column(unique = true)
  private String lastName;

  @JsonIgnore
  @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL)
  private List<Car> cars;

  public Owner(String firstName, String lastName) {
    this.firstName = firstName;
    this.lastName = lastName;
  }
}
