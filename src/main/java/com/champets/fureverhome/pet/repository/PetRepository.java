package com.champets.fureverhome.pet.repository;

import com.champets.fureverhome.pet.model.Pet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PetRepository extends JpaRepository<Pet, Long> {
}
