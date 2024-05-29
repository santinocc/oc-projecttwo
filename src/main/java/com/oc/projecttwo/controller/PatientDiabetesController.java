package com.oc.projecttwo.controller;

import com.oc.projecttwo.service.PatientAssessmentService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/assess/diabetes")                // MODIFIED HERE VS PER REQUIREMENT, DUE TO ADD POSSIBLE OTHER LONG TERM SICKNESS AS AN OPTION (ie. cancer, 
public class PatientDiabetesController {

	
    @Autowired
    private PatientAssessmentService patientAssessmentService;


    @GetMapping("/{id}")
    public String assessDiabetesById(@PathVariable Long id) {
        return patientAssessmentService.assessDiabetesById(id);
    }
    
    @GetMapping("/{family}/{givenName}")
    public String assessDiabetesByFamily(@PathVariable String family, @PathVariable String givenName) {
    	return patientAssessmentService.assessDiabetesByFamily(family, givenName);
    }


}