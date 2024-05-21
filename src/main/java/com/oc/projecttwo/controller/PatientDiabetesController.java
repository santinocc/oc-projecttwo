package com.oc.projecttwo.controller;

//import com.oc.projecttwo.model.PatientHistories;
//import com.oc.projecttwo.model.PatientHistory;
//import com.oc.projecttwo.model.PatientNote;
//import com.oc.projecttwo.repository.PatientHistoryRepository;
import com.oc.projecttwo.service.PatientAssessmentService;

//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

//import java.util.List;
//import java.util.Optional;

@RestController
@RequestMapping("/assess/diabetes")                // MODIFY HERE VS PER REQUIREMENT, DUE TO ADD POSSIBLE OTHER LONG TERM SICKNESS AS AN OPTION (ie. cancer, 
public class PatientDiabetesController {

//    private final Logger logger = LoggerFactory.getLogger(PatientHistoryController.class);
//    private final PatientHistoryRepository patientHistoryRepository;
//
//    public PatientHistoryController(final PatientHistoryRepository patientHistoryRepository) {
//        this.patientHistoryRepository = patientHistoryRepository;
//    }
	
    @Autowired
    private PatientAssessmentService patientAssessmentService;

//    @GetMapping
//    public List<PatientHistory> findAll() {
//        return patientHistoryService.findAll();
//    }

    @GetMapping("/{id}")
    public String assessDiabetesById(@PathVariable Long id) {
        return patientAssessmentService.assessDiabetesById(id);
    }

    @ResponseStatus(HttpStatus.CREATED) // 201
    @PostMapping("/{familyName}")
    public String assessDiabetesByFamily(@RequestBody String family) {
    	return patientAssessmentService.assessDiabetesByFamily(family);
    }

//    @ResponseStatus(HttpStatus.NO_CONTENT) // 204
//    @DeleteMapping("/{familyName}")
//    public void deleteByPatId(@PathVariable Long patId) {
//        patientHistoryService.deleteByPatId(patId);
//    }

}