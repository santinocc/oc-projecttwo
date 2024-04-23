package com.oc.projecttwo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class PatientHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	private Long patId;
    public String note;

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
}