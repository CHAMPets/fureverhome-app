package com.champets.fureverhome.application.repository;

import com.champets.fureverhome.application.model.Application;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ApplicationRepository extends JpaRepository<Application, Long> {

//    @Query("SELECT a from Application a WHERE a.user.emailAddress LIKE CONCAT('%', :query, '%')")
//    List<Application> searchApplicationsByEmailAddress(String emailAddress);

}
