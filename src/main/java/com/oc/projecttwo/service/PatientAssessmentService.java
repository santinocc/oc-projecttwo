package com.oc.projecttwo.service;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
//import java.util.ArrayList;
import java.util.List;
//import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.oc.projecttwo.model.PatientHistory;
//import com.oc.projecttwo.model.PatientNote;
import com.oc.projecttwo.repository.PatientHistoryRepository;
import com.oc.projecttwo.repository.PatientRepository;

@Service
public class PatientAssessmentService {

    @Autowired
    private PatientHistoryRepository patientHistoryRepository;
    
    @Autowired
    private PatientRepository patientRepository;

    

	public Integer calculateAge(Long id) {
		String birthdate = patientRepository.getReferenceById(id).dob;
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
		LocalDate dob = LocalDate.parse(birthdate, formatter);
		LocalDate dateNow = LocalDate.now();
		
		return Period.between(dob, dateNow).getYears();
	}
    
    public String assessDiabetesById(Long id) {
    	
    	String sex = patientRepository.getReferenceById(id).sex;
    	Integer terms = 0; // NEED TO DEVELOP SOLUTION FOR TERMS
    	
        return "Patient: " + id + " (age " + calculateAge(id) + ") diabetes assessment is: " + diabetesStatus(calculateAge(id), sex, terms, id);
    }

    public String assessDiabetesByFamily(String family) {
    	
    	Long id = patientRepository.findByFamily(family).get(0).getId();
    	String sex = patientRepository.getReferenceById(id).sex;
    	Integer terms = 0; // NEED TO DEVELOP SOLUTION FOR TERMS
    	
    	return "Patient: " + family + " (age " + calculateAge(id) + ") diabetes assessment is: " + diabetesStatus(calculateAge(id), sex, terms, id);
    }

    public String diabetesStatus(Integer age, String sex, Integer terms, Long patId) {
    	List<PatientHistory> patientHistory = patientHistoryRepository.findNotesByPatId(patId);
    	
    	if (patientHistory == null) {
    		return ("None");
    	} else if (age >= 30 && terms == 2) {
    		return ("Borderline");
    	} else if ((age < 30 && sex == "M" && terms == 3) || (age < 30 && sex == "F" && terms == 4) || (age >= 30 && terms == 6)) {
    		return ("In Danger");
    	} else if ((age < 30 && sex == "M" && terms == 5) || (age < 30 && sex == "F" && terms == 7) || (age >= 30 && terms == 8)) {
    		return ("Early Onset");
    	} else {
    		return ("New Case. It needs to be added");
    	}
    }

}