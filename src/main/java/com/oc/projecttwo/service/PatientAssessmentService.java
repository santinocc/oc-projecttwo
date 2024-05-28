package com.oc.projecttwo.service;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
//import java.util.ArrayList;
import java.util.List;
//import java.util.Optional;
import java.util.Map;

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

    
    
    public String assessDiabetesById(Long id) {
    	
    	String family = patientRepository.getReferenceById(id).getFamily();
    	String sex = patientRepository.getReferenceById(id).getSex();
    	Integer terms = 0; // NEED TO DEVELOP SOLUTION FOR TERMS
    	Integer age = calculateAge(id);
    	String diabetesAnswer = diabetesStatus(calculateAge(id), sex, terms, id);
    	
        return "Patient: " + id + " " + family +" (age " + age + ") diabetes assessment is: " + diabetesAnswer + " due to sex: " + sex + " & " + terms + " terms.";
    }

    public String assessDiabetesByFamily(String family) {
    	
    	Long id = patientRepository.findByFamily(family).getId();
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
    	} else if (age >= 30 && terms >= 2) {
    		return ("Borderline");
    	} else if ((age < 30 && sex.equals("M") && terms >= 3) || (age < 30 && sex.equals("F") && terms >= 4) || (age >= 30 && terms >= 6)) {
    		return ("In Danger");
    	} else if ((age < 30 && sex.equals("M") && terms >= 5) || (age < 30 && sex.equals("F") && terms >= 7) || (age >= 30 && terms >= 8)) {
    		return ("Early Onset");
    	} else {
    		return ("None.");
    	}
    }
    
//    THIS IS FOR CALCULATE TERMS WITH FULL GEMINI APPROACH
//    public Integer calculateTermss(Long patId) {
//    	
//    	List<String> notesHistory = patientHistoryRepository.findById(patId).get().getNotes();
//    	
//    	private static final String[] wordsToFind = {
//      	      "Hemoglobin A1C", "Microalbumin", "Body Height", "Body Weight",
//      	      "Smoker", "Abnormal", "Cholesterol", "Dizziness", "Relapse", "Reaction", "Antibodies"
//      	  };
//
//    	
//    	for (String notes : notesHistory ) {                 // This because notesHistory HAS notes and each notes HAS words
//      	  Map<String, Integer> countWordsInNotes(String[] notes) {
//      	    Map<String, Integer> wordCounts = new HashMap<>();
//
//      	    // Loop through each note
//      	    for (String note : notes) {
//      	      String lowerCaseNote = note.toLowerCase();  // Convert to lowercase for case-insensitive counting
//      	      for (String wordToFind : wordsToFind) {
//      	        int count = 0;
//      	        int startIndex = 0;
//      	        // Loop through the note to find occurrences of the word
//      	        while ((startIndex = lowerCaseNote.indexOf(wordToFind, startIndex)) != -1) {
//      	          count++;
//      	          // Update starting index to avoid finding overlapping occurrences
//      	          startIndex += wordToFind.length();   // THIS FOR WHAT REASON?
//      	        }
//      	        wordCounts.put(wordToFind, wordCounts.getOrDefault(wordToFind, 0) + count);
//      	      }
//      	    }
//      	    
//      	    System.out.println(wordCounts);
//      	 
//      	    return wordCounts;
//
//      	  }
//    	}
//    }
    
    
//    GEMINI SUGGESTION AS IT IS
//    public class WordCountInNotes {
//
//    	  // Predefined words to count (replace with your actual words)
//    	  private static final String[] wordsToFind = {
//    	      "Hemoglobin A1C", "Microalbumin", "Body Height", "Body Weight",
//    	      "Smoker", "Abnormal", "Cholesterol", "Dizziness", "Relapse", "Reaction", "Antibodies"
//    	  };
//
//    	  public static Map<String, Integer> countWordsInNotes(String[] notes) {
//    	    Map<String, Integer> wordCounts = new HashMap<>();
//
//    	    // Loop through each note
//    	    for (String note : notes) {
//    	      String lowerCaseNote = note.toLowerCase();  // Convert to lowercase for case-insensitive counting
//    	      for (String wordToFind : wordsToFind) {
//    	        int count = 0;
//    	        int startIndex = 0;
//    	        // Loop through the note to find occurrences of the word
//    	        while ((startIndex = lowerCaseNote.indexOf(wordToFind, startIndex)) != -1) {
//    	          count++;
//    	          // Update starting index to avoid finding overlapping occurrences
//    	          startIndex += wordToFind.length();
//    	        }
//    	        wordCounts.put(wordToFind, wordCounts.getOrDefault(wordToFind, 0) + count);
//    	      }
//    	    }
//    	    System.out.println(wordCounts);
//    	 
//    	    return wordCounts;
//    	  }
//
//    	  // ... (rest of the code remains the same)
//    	}
    
 public Integer calculateTerms(Long patId) {
    	
    	List<String> notesHistory = patientHistoryRepository.findById(patId).get().getNotes();
    	
    	final String[] wordsToFind = {
      	      "Hemoglobin A1C", "Microalbumin", "Body Height", "Body Weight",
      	      "Smoker", "Abnormal", "Cholesterol", "Dizziness", "Relapse", "Reaction", "Antibodies"
      	  };
    	
    	Integer terms = 0;

    	for (String notes : notesHistory) {
    		String lowerCaseNotes = notes.toLowerCase();
    		
    		for (String word : wordsToFind) {
    			String lowerCaseWord = word.toLowerCase();
    			
    			if (lowerCaseNotes.contains(lowerCaseWord)) {
    				terms++;
    			}
    		}
    		
    	}
    	
    	return terms;
    	
    }

}