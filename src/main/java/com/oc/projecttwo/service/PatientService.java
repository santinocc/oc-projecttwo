package com.oc.projecttwo.service;

import com.oc.projecttwo.model.Patient;
import com.oc.projecttwo.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class PatientService {

    @Autowired
    private PatientRepository patientRepository;

    public List<Patient> findAll() {
        return patientRepository.findAll();
    }

    public Optional<Patient> findById(Long id) {
        return patientRepository.findById(id);
    }

    public Patient save(Patient patient) {
        return patientRepository.save(patient);
    }

    public void deleteById(Long id) {
        patientRepository.deleteById(id);
    }

    public List<Patient> findByFamily(String family) {
        return patientRepository.findByFamily(family);
    }

    public List<Patient> findByDobAfter(LocalDate date) {
        return patientRepository.findByDobAfter(date);
    }
}