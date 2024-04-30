package com.oc.projecttwo.model;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.annotation.Id;
//import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
//import jakarta.persistence.Id;

//@Entity
@Document("PatientHistory")
public class PatientHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	private Long patId;
    public String note;
    
    
    public PatientHistory(Long patId, String note) {
		super();
		this.patId = patId;
		this.note = note;
	}
    

    // for JPA only, no use
    public PatientHistory() {
    }

    public PatientHistory(String note) {
        this.note = note;
    }

    @Override
    public String toString() {
        return "Patient{" +
                "patId=" + patId +
                ", note='" + note + 
                '}';
    }

    public Long getPatId() {
        return patId;
    }

    public void setPatId(Long patId) {
        this.patId = patId;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
    
    public static PatientHistory update(final Long patId, final PatientHistory patientHistory) {
        final PatientHistory patientHistoryUpdated =  new PatientHistory();
        patientHistoryUpdated.setPatId(patId);
        patientHistoryUpdated.setNote(patientHistory.getNote() == null ? null : patientHistory.getNote());

        return patientHistoryUpdated;
    }
}