package com.oc.projecttwo;

import com.oc.projecttwo.model.PatientHistory;
import com.oc.projecttwo.repository.PatientHistoryRepository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.context.ContextConfiguration;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @DataJpaTest 1. It scans the `@Entity` classes and Spring Data JPA repositories.
 * 2. Set the `spring.jpa.show-sql` property to true and enable the SQL queries logging.
 * 3. Default, JPA test data are transactional and roll back at the end of each test;
 * it means we do not need to clean up saved or modified table data after each test.
 * 4. Replace the application DataSource, run and configure the embed database on classpath.
 */

@ContextConfiguration(classes = MongoDBTestContainerConfig.class)
@DataMongoTest
//@DataJpaTest
// We provide the `test containers` as DataSource, don't replace it.
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
//  activate automatic startup and stop of containers
@Testcontainers
public class PatientHistoryRepositoryTest {

    @Autowired
    private PatientHistoryRepository patientHistoryRepository;

    @Test
    public void testSave() {

        PatientHistory p1 = new PatientHistory();
        p1.setPatId(1L);
        List<String> list = new ArrayList<String>();
        list.add("PatientHistoryNote A");
        p1.setNotes(list);

        //testEM.persistAndFlush(p1); the same
        patientHistoryRepository.save(p1);

        Long savedPatientID = p1.getPatId();

        PatientHistory patientHistory = patientHistoryRepository.findById(savedPatientID).orElseThrow();
        // Patient patient = testEM.find(Patient.class, savedPatientID);

        assertEquals(savedPatientID, patientHistory.getPatId());
        assertEquals("PatientHistoryNote A", patientHistory.getNotes().get(0));


    }

//    @Test
//    public void testUpdate() {
//
//        PatientHistory p1 = new PatientHistory();
//        p1.setPatId(1L);
//        ArrayList<String> list = new ArrayList<String>();
//        list.add("PatientHistoryNote A");
//        list.add("Extra Note");
//        p1.setNotes(list);
//
//        //testEM.persistAndFlush(p1);
//        patientHistoryRepository.save(p1);
//
//        // update price from 9.99 to 19.99
//        p1.setNotes(["PatientGiven AA"]); 
//
//        // update
//        patientHistoryRepository.save(p1);
//
//        List<PatientHistory> result = patientHistoryRepository.findNoteByPatId(1L);
//
//        assertEquals(1, result.size());
//
//        PatientHistory patientHistory = result.get(0);
//        assertNotNull(patientHistory.getPatId());
//        assertTrue(patientHistory.getPatId() > 0);
//
//        assertEquals(1L, patientHistory.getPatId());
//        assertEquals(["PatientHistoryNote A","Extra Note"], patientHistory.getNotes());
//
//    }

    @Test
    public void testFindNotesByPatId() {

        PatientHistory p1 = new PatientHistory();
        p1.setPatId(1L);
        ArrayList<String> list = new ArrayList<String>();
        list.add("PatientHistoryNote A");
        p1.setNotes(list);

        patientHistoryRepository.save(p1);

        List<PatientHistory> result = patientHistoryRepository.findNotesByPatId(1L);

        assertEquals(1, result.size());
        PatientHistory patientHistory = result.get(0);
        assertNotNull(patientHistory.getPatId());
        assertTrue(patientHistory.getPatId() > 0);

        assertEquals(1L, patientHistory.getPatId());
        ArrayList<String> listTest = new ArrayList<String>();
        listTest.add("PatientHistoryNote A");
        assertEquals(listTest, patientHistory.getNotes());

    }

    @Test
    public void testFindAll() {

        PatientHistory p1 = new PatientHistory();
        p1.setPatId(1L);
        ArrayList<String> list1 = new ArrayList<String>();
        list1.add("PatientHistoryNote A");
        p1.setNotes(list1);
        
        PatientHistory p2 = new PatientHistory();
        p2.setPatId(2L);
        ArrayList<String> list2 = new ArrayList<String>();
        list2.add("PatientHistoryNote B");
        p2.setNotes(list2);
        
        PatientHistory p3 = new PatientHistory();
        p3.setPatId(3L);
        ArrayList<String> list3 = new ArrayList<String>();
        list3.add("PatientHistoryNote C");
        p3.setNotes(list3);
        
        PatientHistory p4 = new PatientHistory();
        p4.setPatId(4L);
        ArrayList<String> list4 = new ArrayList<String>();
        list4.add("PatientHistoryNote D");
        p4.setNotes(list4);


        patientHistoryRepository.saveAll(List.of(p1, p2, p3, p4));

        List<PatientHistory> result = patientHistoryRepository.findAll();

        assertTrue(4 <= result.size());

    }

    @Test
    public void testDeleteByPatId() {

        PatientHistory p1 = new PatientHistory();
        p1.setPatId(1L);
        ArrayList<String> list1 = new ArrayList<String>();
        list1.add("PatientHistoryNote A");
        p1.setNotes(list1);

        patientHistoryRepository.save(p1);

        Long savedPatientID = p1.getPatId();

        // Patient patient = patientHistoryRepository.findById(savedPatientID).orElseThrow();
        // Patient patient = testEM.find(Patient.class, savedPatientID);

        patientHistoryRepository.deleteById(savedPatientID);

        Optional<PatientHistory> result = patientHistoryRepository.findById(savedPatientID);
        assertTrue(result.isEmpty());

    }

}
