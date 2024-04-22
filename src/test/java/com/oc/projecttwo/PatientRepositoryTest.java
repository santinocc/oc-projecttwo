package com.oc.projecttwo;

import com.oc.projecttwo.model.Patient;
import com.oc.projecttwo.repository.PatientRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.math.BigDecimal;
import java.time.LocalDate;
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

@DataJpaTest
// We provide the `test containers` as DataSource, don't replace it.
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
//  activate automatic startup and stop of containers
@Testcontainers
public class PatientRepositoryTest {

    @Autowired
    private PatientRepository patientRepository;

    // static, all tests share this
    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>(
            "postgres:15-alpine"
    );

    @Test
    public void testSave() {

        Patient p1 = new Patient("PatientFamily A",
        		"PatientGiven A",
        		"1990-01-01",
        		"M",
        		"1 Brooklyn St",
        		"111-222-3333");

        //testEM.persistAndFlush(p1); the same
        patientRepository.save(p1);

        Long savedPatientID = p1.getId();

        Patient patient = patientRepository.findById(savedPatientID).orElseThrow();
        // Patient patient = testEM.find(Patient.class, savedPatientID);

        assertEquals(savedPatientID, patient.getId());
        assertEquals("PatientFamily A", patient.getFamily());
        assertEquals("PatientGiven A", patient.getGiven());
        assertEquals("1990-01-01", patient.getDob());
        assertEquals("M", patient.getSex());
        assertEquals("1 Brooklyn St", patient.getAddress());
        assertEquals("111-222-3333", patient.getPhone());


    }

    @Test
    public void testUpdate() {

        Patient p1 = new Patient("PatientFamily A",
        		"PatientGiven A",
        		"1990-01-01",
        		"M",
        		"1 Brooklyn St",
        		"111-222-3333");

        //testEM.persistAndFlush(p1);
        patientRepository.save(p1);

        // update price from 9.99 to 19.99
        p1.setGiven("PatientGiven AA");

        // update
        patientRepository.save(p1);

        List<Patient> result = patientRepository.findByFamily("PatientFamily A");

        assertEquals(1, result.size());

        Patient patient = result.get(0);
        assertNotNull(patient.getId());
        assertTrue(patient.getId() > 0);

        assertEquals("PatientFamily A", patient.getFamily());
        assertEquals("PatientGiven AA", patient.getGiven());
        assertEquals("1990-01-01", patient.getDob());
        assertEquals("M", patient.getSex());
        assertEquals("1 Brooklyn St", patient.getAddress());
        assertEquals("111-222-3333", patient.getPhone());

    }

    @Test
    public void testFindByFamily() {

        Patient p1 = new Patient("PatientFamily A",
        		"PatientGiven A",
        		"1990-01-01",
        		"M",
        		"1 Brooklyn St",
        		"111-222-3333");

        patientRepository.save(p1);

        List<Patient> result = patientRepository.findByFamily("PatientFamily A");

        assertEquals(1, result.size());
        Patient patient = result.get(0);
        assertNotNull(patient.getId());
        assertTrue(patient.getId() > 0);

        assertEquals("PatientFamily A", patient.getFamily());
        assertEquals("PatientGiven A", patient.getGiven());
        assertEquals("1990-01-01", patient.getDob());
        assertEquals("M", patient.getSex());
        assertEquals("1 Brooklyn St", patient.getAddress());
        assertEquals("111-222-3333", patient.getPhone());

    }

    @Test
    public void testFindAll() {

        Patient p1 = new Patient("PatientFamily A",
        		"PatientGiven A",
        		"1990-01-01",
        		"M",
        		"1 Brooklyn St",
        		"111-222-3333");
        Patient p2 = new Patient("PatientFamily B",
        		"PatientGiven B",
        		"1991-01-01",
        		"F",
        		"2 Brooklyn St",
        		"444-555-6666");
        Patient p3 = new Patient("PatientFamily C",
        		"PatientGiven C",
        		"2000-01-01",
        		"M",
        		"3 Chicago St",
        		"777-888-9999");
        Patient p4 = new Patient("PatientFamily D",
        		"PatientGiven D",
        		"2005-01-01",
        		"F",
        		"4 Chicago St",
        		"111-888-9999");

        patientRepository.saveAll(List.of(p1, p2, p3, p4));

        List<Patient> result = patientRepository.findAll();
        assertEquals(4, result.size());

    }

    @Test
    public void testDeleteById() {

        Patient p1 = new Patient("PatientFamily A",
        		"PatientGiven A",
        		"1990-01-01",
        		"M",
        		"1 Brooklyn St",
        		"111-222-3333");

        patientRepository.save(p1);

        Long savedPatientID = p1.getId();

        // Patient patient = patientRepository.findById(savedPatientID).orElseThrow();
        // Patient patient = testEM.find(Patient.class, savedPatientID);

        patientRepository.deleteById(savedPatientID);

        Optional<Patient> result = patientRepository.findById(savedPatientID);
        assertTrue(result.isEmpty());

    }

}
