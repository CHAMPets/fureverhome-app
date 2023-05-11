package com.champets.fureverhome.vaccine.repository;

import com.champets.fureverhome.vaccine.model.VaccinePet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VaccinePetRepository extends JpaRepository<VaccinePet, Long> {
    @Query("SELECT p from VaccinePet p WHERE p.pet.id = :petId")
    List<VaccinePet> findVaccinesByPetId(Long petId);

    @Query("SELECT p from VaccinePet p WHERE p.vaccine.id = :vaccineId")
    List<VaccinePet> findPetsWithVaccineId(Long vaccineId);
}
