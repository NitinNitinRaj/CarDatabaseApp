package com.nr.cardatabase.service.impl;

import com.nr.cardatabase.entities.Owner;
import com.nr.cardatabase.exception.EntityNotFoundExceptionHandler;
import com.nr.cardatabase.repository.OwnerRepository;
import com.nr.cardatabase.service.OwnerService;
import java.util.List;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class OwnerServiceImpl implements OwnerService {

  private final OwnerRepository ownerRepository;

  @Override
  public List<Owner> getAllOwners() {
    return (List<Owner>) ownerRepository.findAll();
  }

  @Override
  public Owner getOwner(Long id) {
    return unWarp(id, ownerRepository.findById(id));
  }

  @Override
  public void deleteOwner(Long id) {
    ownerRepository.deleteById(id);
  }

  @Override
  public Owner saveOwner(Owner owner) {
    return ownerRepository.save(owner);
  }

  @Override
  public Owner updateOwner(Owner owner, Long id) {
    Owner ownerDb = unWarp(id, ownerRepository.findById(id));
    ownerDb.setFirstName(owner.getFirstName());
    ownerDb.setLastName(owner.getLastName());
    return ownerRepository.save(ownerDb);
  }

  public static Owner unWarp(Long id, Optional<Owner> owner) {
    if (owner.isPresent()) {
      return owner.get();
    } else {
      throw new EntityNotFoundExceptionHandler(id.toString(), Owner.class);
    }
  }
}
