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
//		List<Patient> patients = patientRepository.findAll();
//        boolean isUpdated = false;
        AtomicBoolean isUpdated = new AtomicBoolean(false);

        patientToUpdate.ifPresent(p-> {
        	p.family = patientUpdate.family;
        	p.given = patientUpdate.given;
            p.dob = patientUpdate.dob;
            p.sex = patientUpdate.sex;
            p.address = patientUpdate.address;
            p.phone = patientUpdate.phone;
            
//            isUpdated = true;
            isUpdated.set(true);
        });
        
        if (isUpdated.get()) { 
        	patientRepository.save(patientToUpdate.get()); 
        }
        
//        if (patientToUpdate !=null) {
//        for (Patient patient : patients) {
//            if (patient.family.equals(patientUpdate.family) && patient.given.equals(patientUpdate.given)) {
//                patient.setDob(patientUpdate.dob);
//                patient.setSex(patientUpdate.sex);
//                patient.setAddress(patientUpdate.address);
//                patient.setPhone(patientUpdate.phone);
            	
//        patientToUpdate.dob = patientUpdate.dob;
//        patientToUpdate.sex = patientUpdate.sex;
//        patientToUpdate.address = patientUpdate.address;
//        patientToUpdate.phone = patientUpdate.phone;

//                break;
//            }
//        }
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