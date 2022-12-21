package com.nr.cardatabase.service;

import com.nr.cardatabase.entities.Owner;
import java.util.List;

public interface OwnerService {
  List<Owner> getAllOwners();
  Owner getOwner(Long id);
  void deleteOwner(Long id);
  Owner saveOwner(Owner owner);
  Owner updateOwner(Owner owner, Long id);
}
