package com.oc.projecttwo.service;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.oc.projecttwo.model.Patient;
import com.oc.projecttwo.model.PatientHistory;
import com.oc.projecttwo.repository.PatientHistoryRepository;
import com.oc.projecttwo.repository.PatientRepository;

@Service
public class PatientAssessmentService {

    @Autowired
    private PatientHistoryRepository patientHistoryRepository;
    
    @Autowired
    private PatientRepository patientRepository;

    
    
    public String assessDiabetesById(Long id) {
    	
    	String family = patientRepository.getReferenceById(id).getFamily();
    	String sex = patientRepository.getReferenceById(id).getSex();
    	Integer terms = calculateTerms(id);
    	Integer age = calculateAge(id);
    	String diabetesAnswer = diabetesStatus(calculateAge(id), sex, terms, id);
    	
        return "Patient: " + id + " " + family +" (age " + age + ") diabetes assessment is: " + diabetesAnswer + " due to sex: " + sex + " & " + terms + " terms.";
    }

    public String assessDiabetesByFamily(String family, String givenName) {
    	
    	Patient patient = patientRepository.findByFamilyAndGivenName(family, givenName);
    	Long id = patient.getId();
    	String sex = patientRepository.getReferenceById(id).getSex();    	
    	Integer terms = calculateTerms(id);
    	Integer age = calculateAge(id);
    	String diabetesAnswer = diabetesStatus(calculateAge(id), sex, terms, id);
    	
    	return "Patient: " + family + " (age " + age + ") diabetes assessment is: " + diabetesAnswer + " due to sex: " + sex + " & " + terms + " terms.";
    }
    
	public Integer calculateAge(Long id) {
		
		String birthdate = patientRepository.getReferenceById(id).getDob();
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
		LocalDate dob = LocalDate.parse(birthdate, formatter);
		LocalDate dateNow = LocalDate.now();
		
		return Period.between(dob, dateNow).getYears();
	}

    public String diabetesStatus(Integer age, String sex, Integer terms, Long patId) {
    	
    	List<PatientHistory> patientHistory = patientHistoryRepository.findNotesByPatId(patId);
    	
    	if (patientHistory == null) {
    		return ("None");
    	} else if ((age < 30 && sex.equals("M") && terms >= 5) || (age < 30 && sex.equals("F") && terms >= 7) || (age >= 30 && terms >= 8)) {
    		return ("Early Onset");
    	} else if ((age < 30 && sex.equals("M") && terms >= 3) || (age < 30 && sex.equals("F") && terms >= 4) || (age >= 30 && terms >= 6)) {
    		return ("In Danger");
    	} else if (age >= 30 && terms >= 2) {
    		return ("Borderline");
    	} else {
    		return ("None.");
    	}
    }
    
 public Integer calculateTerms(Long patId) {
    	
    	List<String> notesHistory = patientHistoryRepository.findById(patId).get().getNotes();
    	
    	final String[] wordsToFind = {
      	      "Hemoglobin A1C", "Microalbumin", "Body Height", "Body Weight",
      	      "Smoker", "Abnormal", "Cholesterol", "Dizziness", "Relapse", "Reaction", "Antibodies"
      	  };
    	
    	Integer terms = 0;

    	
    	for (String word : wordsToFind) {
    		String lowerCaseWord = word.toLowerCase();
    		
    		for (String notes : notesHistory) {
    			String lowerCaseNotes = notes.toLowerCase();
    			
    			if (lowerCaseNotes.contains(lowerCaseWord)) {
    				terms++;
    				break;
    			}
    		}
    		
    	}
    	return terms;
    }

}