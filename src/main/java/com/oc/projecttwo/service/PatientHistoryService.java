package com.oc.projecttwo.service;

import com.oc.projecttwo.model.PatientHistory;
import com.oc.projecttwo.repository.PatientHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;

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
    
    // a 'public ResponseEntity<?>' is done instead of 'public boolean'
    
	public ResponseEntity<?> update(PatientHistory patientHistoryUpdate) {
		
		Optional<PatientHistory> patientHistoryToUpdate = patientHistoryRepository.findById(patientHistoryUpdate.getPatId());

		final var responseUpdated = PatientHistory.update(patientHistoryToUpdate.get().getPatId(), patientHistoryUpdate);
		
		return ResponseEntity.ok(patientHistoryRepository.save(responseUpdated));
		
//        AtomicBoolean isUpdated = new AtomicBoolean(false);
//
//        patientHistoryToUpdate.ifPresent(p-> {
//        	p.note = patientHistoryUpdate.note;
//            
//            isUpdated.set(true);
//        });
//        
//        if (isUpdated.get()) { 
//        	patientHistoryRepository.save(patientHistoryToUpdate.get()); 
//        }
//        
//        return isUpdated.get();
	}

    public void deleteByPatId(Long patId) {
        patientHistoryRepository.deleteById(patId);
    }
    
    public List<PatientHistory> findNoteByPatId(Long patId) {
        return patientHistoryRepository.findNoteByPatId(patId);
    }

}