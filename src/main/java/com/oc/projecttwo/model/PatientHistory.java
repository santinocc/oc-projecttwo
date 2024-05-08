package com.oc.projecttwo.model;

import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

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
    public  List<String> notes;
    
    
    public PatientHistory(Long patId, List<String> notes) {
		super();
		this.patId = patId;
		this.notes = notes;
	}
    

    // for JPA only, no use
    public PatientHistory() {
    }

    public PatientHistory(List<String> notes) {
        this.notes = notes;
    }

    @Override
    public String toString() {
        return "Patient{" +
                "patId=" + patId +
                ", notes='" + notes + 
                '}';
    }

    public Long getPatId() {
        return patId;
    }

    public void setPatId(Long patId) {
        this.patId = patId;
    }

    public List<String> getNotes() {
        return notes;
    }

    public void setNotes(List<String> notes) {
        this.notes = notes;
    }
    
    public void addNote(String note) {
        notes.add(note);
    }
    
}