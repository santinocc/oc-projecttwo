package com.oc.projecttwo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.oc.projecttwo.model.PatientHistory;

// Spring Data JPA creates CRUD implementation at runtime automatically.
public interface PatientHistoryRepository extends JpaRepository<PatientHistory, Long> {

}
