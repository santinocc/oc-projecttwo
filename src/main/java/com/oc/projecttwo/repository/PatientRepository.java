package com.oc.projecttwo.repository;

import com.oc.projecttwo.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

// Spring Data JPA creates CRUD implementation at runtime automatically.
public interface PatientRepository extends JpaRepository<Patient, Long> {

    List<Patient> findByFamily(String family);

    // Custom query
    @Query("SELECT p FROM Patient p WHERE p.family = ?1 AND p.given = ?2")
    Patient findByFamilyAndGivenName(String family, String givenName);

}
