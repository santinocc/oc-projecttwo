package com.oc.projecttwo.controller;

import com.oc.projecttwo.model.Patient;
import com.oc.projecttwo.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/patients")
public class PatientController {

    @Autowired
    private PatientService patientService;

    @GetMapping
    public List<Patient> findAll() {
        return patientService.findAll();
    }

    @GetMapping("/{id}")
    public Optional<Patient> findById(@PathVariable Long id) {
        return patientService.findById(id);
    }

    // create a patient
    @ResponseStatus(HttpStatus.CREATED) // 201
    @PostMapping("/add")
    public Patient create(@RequestBody Patient patient) {
        return patientService.save(patient);
    }

    // update a patient
    @PutMapping("/update")
    public boolean update(@RequestBody Patient patient) {
        return patientService.update(patient);
    }

    // delete a patient
    @ResponseStatus(HttpStatus.NO_CONTENT) // 204
    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) {
        patientService.deleteById(id);
    }

    @GetMapping("/find/family/{family}")
    public List<Patient> findByFamily(@PathVariable String family) {
        return patientService.findByFamily(family);
    }

//    @GetMapping("/find/dob-after/{date}")       // TRY IF MORE TIME
//    public List<Patient> findByDobAfter(
//            @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date) {
//        return patientService.findByDobAfter(date);
//    }

}