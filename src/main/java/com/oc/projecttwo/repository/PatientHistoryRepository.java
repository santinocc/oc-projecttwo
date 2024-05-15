package com.oc.projecttwo.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.oc.projecttwo.model.PatientHistory;

// Spring Data JPA creates CRUD implementation at runtime automatically.
public interface PatientHistoryRepository extends MongoRepository<PatientHistory, Long> {

	List<PatientHistory> findNotesByPatId(Long patId);
}
