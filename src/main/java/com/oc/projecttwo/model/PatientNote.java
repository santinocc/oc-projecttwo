package com.oc.projecttwo.model;

import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

import org.springframework.data.annotation.Id;
//import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
//import jakarta.persistence.Id;

//@Entity
@Document("PatientNote")
public class PatientNote {

	private Long patId;
    public  String note;
    
    
    public PatientNote(Long patId, String note) {
		super();
		this.patId = patId;
		this.note = note;
	}
    

    // for JPA only, no use
    public PatientNote() {
    }

    public PatientNote(String note) {
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
    
}