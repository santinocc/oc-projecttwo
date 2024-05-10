package com.oc.projecttwo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.oc.projecttwo.model.PatientHistory;
import com.oc.projecttwo.model.PatientNote;
import com.oc.projecttwo.repository.PatientHistoryRepository;

@Service
public class PatientHistoryService {

    @Autowired
    private PatientHistoryRepository patientHistoryRepository;

    public List<PatientHistory> findAll() {
        return patientHistoryRepository.findAll();
    }

    public Optional<PatientHistory> findByPatId(Long patId) {
        return patientHistoryRepository.findById(patId);
    }

    public PatientHistory save(PatientHistory patientHistory) {
    	
        return patientHistoryRepository.save(patientHistory);
    }

    public void deleteByPatId(Long patId) {
        patientHistoryRepository.deleteById(patId);
    }
    
    public List<PatientHistory> findNoteByPatId(Long patId) {
        return patientHistoryRepository.findNoteByPatId(patId);
    }

	public PatientHistory addPatientNote(PatientNote patientNote) {
		
    	Optional<PatientHistory> existingNotes = findByPatId(patientNote.getPatId());

    	if (existingNotes.isPresent()) {
    	    PatientHistory patientHistory = existingNotes.get();
    	    List<String> notes = patientHistory.getNotes();
    	    if (notes == null) {
    	        patientHistory.setNotes(new ArrayList<String>());
    	    } 
    	    patientHistory.addNote(patientNote.getNote());
    	    return save(patientHistory);
    	}
    	
    	Long patId = patientNote.getPatId();
    	List<String> notes = new ArrayList<String>();
    	notes.add(patientNote.getNote());
    	
    	PatientHistory patientHistory = new PatientHistory(patId, notes);
    	return patientHistoryRepository.insert(patientHistory); //CREATE A NEW PATIENT
	}

}