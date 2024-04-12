package com.oc.projecttwo.repository;

import com.oc.projecttwo.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

// Spring Data JPA creates CRUD implementation at runtime automatically.
public interface PatientRepository extends JpaRepository<Patient, Long> {

    List<Patient> findByFamily(String family);

    // Custom query
//    @Query("SELECT b FROM Patient b WHERE b.dob > :date")        //PENDING: Dob model class needs to be 'LocalDate' instead of 'String'
//    List<Patient> findByDobAfter(@Param("date") LocalDate date);

}
