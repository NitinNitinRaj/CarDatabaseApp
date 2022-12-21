package com.nr.cardatabase.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Car {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotBlank(message = "Brand cannot be blank")
  private String brand;

  @NotBlank(message = "Model cannot be blank")
  private String model;

  @NotBlank(message = "Color cannot be blank")
  private String color;

  @NotBlank(message = "RegisterNumber cannot be blank")
  @Column(unique = true)
  private String registerNumber;

  @NotNull
  @Column(name = "model_year")
  private int year;

  @NotNull
  @Min(value = 0L, message = "Price cannot be negative")
  private double price;

  @JsonIgnore
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "owner_id", referencedColumnName = "id")
  private Owner owner;

  public Car(
    String brand,
    String model,
    String color,
    String registerNumber,
    int year,
    double price,
    Owner owner
  ) {
    this.brand = brand;
    this.model = model;
    this.color = color;
    this.registerNumber = registerNumber;
    this.year = year;
    this.price = price;
    this.owner = owner;
  }
}
