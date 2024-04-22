package com.oc.projecttwo;

import com.oc.projecttwo.model.Patient;
import com.oc.projecttwo.repository.PatientRepository;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.test.context.TestPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

import static io.restassured.RestAssured.given;
import static io.restassured.config.JsonConfig.jsonConfig;
import static io.restassured.path.json.config.JsonPathConfig.NumberReturnType.BIG_DECIMAL;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
// activate automatic startup and stop of containers
@Testcontainers
// JPA drop and create table, good for testing
@TestPropertySource(properties = {"spring.jpa.hibernate.ddl-auto=create-drop"})
public class PatientControllerTest {

    @LocalServerPort
    private Integer port;

    @Autowired
    PatientRepository patientRepository;

    // static, all tests share this postgres container
    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>(
            "postgres:15-alpine"
    );

    @BeforeEach
    void setUp() {
        RestAssured.baseURI = "http://localhost:" + port;
        patientRepository.deleteAll();

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
    }

    @Test
    void testFindAll() {

        given()
                .contentType(ContentType.JSON)
                .when()
                    .get("/patients")
                .then()
                    .statusCode(200)    // expecting HTTP 200 OK
                    .contentType(ContentType.JSON) // expecting JSON response content
                    .body(".", hasSize(4));

    }

    @Test
    void testFindByFamily() {

        String family = "PatientFamily C";

        given()
                //Returning floats and doubles as BigDecimal
                .config(RestAssured.config().jsonConfig(jsonConfig().numberReturnType(BIG_DECIMAL)))
                .contentType(ContentType.JSON)
                .pathParam("family", family)
                .when()
                    .get("/patients/find/family/{family}")
                .then()
                    .statusCode(200)
                    .contentType(ContentType.JSON)
                    .body(
                        ".", hasSize(1),
                        "[0].family", equalTo("PatientFamily C"),
                        "[0].given", equalTo("PatientGiven C"),
                        "[0].dob", equalTo("2000-01-01"),
                        "[0].sex", equalTo("M"),
                        "[0].address", equalTo("3 Chicago St"),
                        "[0].phone", equalTo("777-888-9999")
                );
    }

    @Test
    public void testDeleteById() {
        Long id = 1L; // replace with a valid ID
        given()
                .pathParam("id", id)
                .when()
                    .delete("/patients/{id}")
                .then()
                    .statusCode(204); // expecting HTTP 204 No Content
    }

    @Test
    public void testCreate() {

        given()
                .contentType(ContentType.JSON)
                .body("{ \"family\": \"PatientFamily E\", \"given\": \"PatientGiven E\", \"dob\": \"2023-09-14\", \"sex\": \"M\", \"address\": \"5 NY St\", \"phone\": \"000-111-0000\" }")
                .when()
                    .post("/patients/add")
                .then()
                    .statusCode(201) // expecting HTTP 201 Created
                    .contentType(ContentType.JSON); // expecting JSON response content

        // find the new saved patient
        given()
                //Returning floats and doubles as BigDecimal
                .config(RestAssured.config().jsonConfig(jsonConfig().numberReturnType(BIG_DECIMAL)))
                .contentType(ContentType.JSON)
                .pathParam("family", "PatientFamily E")
                .when()
                    .get("/patients/find/family/{family}")
                .then()
                    .statusCode(200)
                    .contentType(ContentType.JSON)
                    .body(
                        ".", hasSize(1),
                        "[0].family", equalTo("PatientFamily E"),
                        "[0].given", equalTo("PatientGiven E"),
                        "[0].dob", equalTo("2023-09-14"),
                        "[0].sex", equalTo("M"),
                        "[0].address", equalTo("5 NY St"),
                        "[0].phone", equalTo("000-111-0000")
                    );
    }

    /**
     * Patient p4
     */
    @Test
    public void testUpdate() {

        Patient patientD = patientRepository.findByFamily("PatientFamily D").get(0);
        System.out.println(patientD);

        Long id = patientD.getId();

        patientD.setFamily("PatientFamily E");
        patientD.setGiven("PatientGiven E");
        patientD.setDob("1950-08-10");
        patientD.setSex("F");
        patientD.setAddress("78 Oregon St");
        patientD.setPhone("555-444-1212");

        given()
                .contentType(ContentType.JSON)
                .body(patientD)
                .when()
                    .put("/patients/update")
                .then()
                    .statusCode(200)
                    .contentType(ContentType.JSON);

        // get the updated patient
        Patient updatedPatient = patientRepository.findById(id).orElseThrow();
        System.out.println(updatedPatient);

        assertEquals(id, updatedPatient.getId());
        assertEquals("PatientFamily E", updatedPatient.getFamily());
        assertEquals("PatientGiven E", updatedPatient.getGiven());
        assertEquals("1950-08-10", updatedPatient.getDob());
        assertEquals("F", updatedPatient.getSex());
        assertEquals("78 Oregon St", updatedPatient.getAddress());
        assertEquals("555-444-1212", updatedPatient.getPhone());
        
    }


}