package com.oc.projecttwo.service;

import com.oc.projecttwo.model.Patient;
import com.oc.projecttwo.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;

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
    
	public boolean update(Patient patientUpdate) {
		
		Optional<Patient> patientToUpdate = patientRepository.findById(patientUpdate.getId());

        AtomicBoolean isUpdated = new AtomicBoolean(false);

        patientToUpdate.ifPresent(p-> {
        	p.family = patientUpdate.family;
        	p.given = patientUpdate.given;
            p.dob = patientUpdate.dob;
            p.sex = patientUpdate.sex;
            p.address = patientUpdate.address;
            p.phone = patientUpdate.phone;
            
            isUpdated.set(true);
        });
        
        if (isUpdated.get()) { 
        	patientRepository.save(patientToUpdate.get()); 
        }
        
        return isUpdated.get();
	}

    public void deleteById(Long id) {
        patientRepository.deleteById(id);
    }

    public List<Patient> findByFamily(String family) {
        return patientRepository.findByFamily(family);
    }

//    public List<Patient> findByDobAfter(LocalDate date) {   // IF THERE IS EXTRA TIME
//        return patientRepository.findByDobAfter(date);
//    }
}