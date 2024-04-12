package com.oc.projecttwo.service;

import com.oc.projecttwo.model.Patient;
import com.oc.projecttwo.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    
	public boolean update(Patient patientUpdate) {
		
		List<Patient> patients = patientRepository.findAll();
        boolean isUpdated = false;

        for (Patient patient : patients) {
            if (patient.family.equals(patientUpdate.family) && patient.given.equals(patientUpdate.given)) {
//                patient.setDob(patientUpdate.dob);
//                patient.setSex(patientUpdate.sex);
//                patient.setAddress(patientUpdate.address);
//                patient.setPhone(patientUpdate.phone);
            	
                patient.dob = patientUpdate.dob;
                patient.sex = patientUpdate.sex;
                patient.address = patientUpdate.address;
                patient.phone = patientUpdate.phone;

                isUpdated = true;
                break;
            }
        }
        return isUpdated;
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