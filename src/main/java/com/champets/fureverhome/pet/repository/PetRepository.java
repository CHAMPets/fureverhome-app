package com.champets.fureverhome.pet.repository;

import com.champets.fureverhome.pet.enums.BodySize;
import com.champets.fureverhome.pet.enums.Gender;
import com.champets.fureverhome.pet.enums.Type;
import com.champets.fureverhome.pet.model.dto.PetDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.champets.fureverhome.pet.model.Pet;

import java.util.List;
import java.util.Optional;

@Repository
public interface PetRepository extends JpaRepository<Pet, Long> {
    List<Pet> findByActiveTrue();

    @Query("SELECT p FROM Pet p WHERE p.active = true AND NOT EXISTS "
            + "(SELECT a FROM Application a WHERE a.pet = p AND a.user.id = :userId) "
            + "AND (:type is null or p.type = :type) and (:size is null or p.bodySize = :size) and (:gender is null or p.gender = :gender)")
    List<Pet> findActivePetsNotAppliedByUserWithFilter(@Param("userId") Long userId, @Param("type") Type type, @Param("size") BodySize size, @Param("gender") Gender gender);


    @Query("SELECT p FROM Pet p WHERE (:type is null or p.type = :type) and (:size is null or p.bodySize = :size) and (:gender is null or p.gender = :gender)")
    List<Pet> findPetsByFilter(Type type, BodySize size, Gender gender);

    List<Pet> findAllByOrderByCreatedDateDesc();
    @Query("SELECT p FROM Pet p WHERE p.id NOT IN (SELECT a.pet.id FROM Application a WHERE a.user.id = :userId)")
    List<Pet> findPetsNotAppliedByUser(@Param("userId") Long userId);

    @Query("SELECT p FROM Pet p WHERE p.active = true AND NOT EXISTS "
 + "(SELECT a FROM Application a WHERE a.pet = p AND a.user.id = :userId)")
    List<Pet> findActivePetsNotAppliedByUser(@Param("userId") Long userId);

}
