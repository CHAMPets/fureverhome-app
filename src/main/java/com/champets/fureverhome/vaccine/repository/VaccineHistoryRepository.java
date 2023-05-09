package com.champets.fureverhome.vaccine.repository;

import com.champets.fureverhome.user.model.User;
import com.champets.fureverhome.vaccine.model.VaccineHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VaccineHistoryRepository extends JpaRepository<VaccineHistory, Long> {
}
