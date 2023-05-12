package com.champets.fureverhome.vaccine.repository;

import com.champets.fureverhome.vaccine.model.Vaccine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VaccineRepository extends JpaRepository<Vaccine, Long> {

}
