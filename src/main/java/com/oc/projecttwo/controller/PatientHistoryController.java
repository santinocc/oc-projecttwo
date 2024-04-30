package com.oc.projecttwo.controller;

import com.oc.projecttwo.model.PatientHistory;
import com.oc.projecttwo.repository.PatientHistoryRepository;
import com.oc.projecttwo.service.PatientHistoryService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/patHistory")
public class PatientHistoryController {

    private final Logger logger = LoggerFactory.getLogger(PatientHistoryController.class);
    private final PatientHistoryRepository patientHistoryRepository;

    public PatientHistoryController(final PatientHistoryRepository patientHistoryRepository) {
        this.patientHistoryRepository = patientHistoryRepository;
    }
	
    @Autowired
    private PatientHistoryService patientHistoryService;

    @GetMapping
    public List<PatientHistory> findAll() {
        return patientHistoryService.findAll();
    }

    @GetMapping("/{patId}")
    public Optional<PatientHistory> findByPatId(@PathVariable Long patId) {
        return patientHistoryService.findByPatId(patId);
    }

    // create a patientHistory
    @ResponseStatus(HttpStatus.CREATED) // 201
    @PostMapping("/add")
    public PatientHistory create(@RequestBody PatientHistory patientHistory) {
        return patientHistoryService.save(patientHistory);
    }

    // update a patientHistory
    @PutMapping("/update")
    public ResponseEntity<?> update(@RequestBody PatientHistory patientHistory) {
    	
        final var response = patientHistoryRepository.findById(patientHistory.getPatId());
        if(response.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

    	//        return patientHistoryService.update(patientHistory);
    	
    	final var responseUpdated = PatientHistory.update(patientHistory.getPatId(), patientHistory);
    	
    	return ResponseEntity.ok(patientHistoryRepository.save(responseUpdated));
    }

    // delete a patientHistory
    @ResponseStatus(HttpStatus.NO_CONTENT) // 204
    @DeleteMapping("/{patId}")
    public void deleteByPatId(@PathVariable Long patId) {
        patientHistoryService.deleteByPatId(patId);
    }

}