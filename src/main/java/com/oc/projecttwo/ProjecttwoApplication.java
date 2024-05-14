package com.oc.projecttwo;

//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
//import com.oc.projecttwo.repository.PatientHistoryRepository;


@SpringBootApplication
//@EnableMongoRepositories
public class ProjecttwoApplication {
	
//	@Autowired
//	PatientHistoryRepository patientHistoryRepository;

	public static void main(String[] args) {
		SpringApplication.run(ProjecttwoApplication.class, args);
	}

}
