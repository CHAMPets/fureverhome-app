package com.champets.fureverhome.vaccine.repository;

import com.champets.fureverhome.vaccine.model.VaccinePet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VaccinePetRepository extends JpaRepository<VaccinePet, Long> {
}
