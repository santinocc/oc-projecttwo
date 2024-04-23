package com.oc.projecttwo.controller;

import com.oc.projecttwo.model.PatientHistory;
import com.oc.projecttwo.service.PatientHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/patHistory")
public class PatientHistoryController {

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
    public boolean update(@RequestBody PatientHistory patientHistory) {
        return patientHistoryService.update(patientHistory);
    }

    // delete a patientHistory
    @ResponseStatus(HttpStatus.NO_CONTENT) // 204
    @DeleteMapping("/{patId}")
    public void deleteByPatId(@PathVariable Long patId) {
        patientHistoryService.deleteByPatId(patId);
    }

}