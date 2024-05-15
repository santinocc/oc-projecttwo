package com.oc.projecttwo;

import com.oc.projecttwo.model.PatientHistory;
import com.oc.projecttwo.repository.PatientHistoryRepository;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.test.context.TestPropertySource;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
// activate automatic startup and stop of containers
@Testcontainers
// JPA drop and create table, good for testing
@TestPropertySource(properties = {"spring.jpa.hibernate.ddl-auto=create-drop"})
public class PatientHistoryControllerTest {

    @LocalServerPort
    private Integer port;

    @Autowired
    PatientHistoryRepository patientHistoryRepository;

    // static, all tests share this mongodb container
    @Container
    @ServiceConnection
    static MongoDBContainer mongodb = new MongoDBContainer(
            "mongo"
    );

    @BeforeEach
    void setUp() {
        RestAssured.baseURI = "http://localhost:" + port;
        patientHistoryRepository.deleteAll();

        List<String> a1 = new ArrayList<String>();
        a1.add("Note Test 1");
        PatientHistory p1 = new PatientHistory(1L,a1);
        List<String> a2 = new ArrayList<String>();
        a2.add("Note Test 1");
        a2.add("Note Test 2");
        PatientHistory p2 = new PatientHistory(2L,a2);
        List<String> a3 = new ArrayList<String>();
        a3.add("Note Test 1");
        PatientHistory p3 = new PatientHistory(3L, a3);
        List<String> a4 = new ArrayList<String>();
        a4.add("Note Test 1");
        a4.add("Note Test 2");
        a4.add("Note Test 3");
        PatientHistory p4 = new PatientHistory(4L,a4);

        patientHistoryRepository.saveAll(List.of(p1, p2, p3, p4));
    }

    @Test
    void testFindAll() {

        given()
                .contentType(ContentType.JSON)
                .when()
                    .get("/patHistory")
                .then()
                    .statusCode(200)    // expecting HTTP 200 OK
                    .contentType(ContentType.JSON) // expecting JSON response content
                    .body(".", hasSize(4));

    }

    @Test
    void testFindByPatId() {

        Long patId = 1L;

        given()
                //Returning floats and doubles as BigDecimal
                .contentType(ContentType.JSON)
                .pathParam("patId", patId)
                .when()
                    .get("/patHistory/{patId}")
                .then()
                    .statusCode(200)
                    .contentType(ContentType.JSON)
                    .body(
                            // Check for existence of "patId" with Hamcrest matcher
                            "$", hasKey("patId"),
                            // Check for existence of "notes" with Hamcrest matcher
                            "$", hasKey("notes"),
                            // Check for "patId" with expected value
                            "patId", equalTo(1),
                            // Check for "notes" with expected value
                            "notes[0]", equalTo("Note Test 1")
                );
    }

    @Test
    public void testDeleteById() {
        Long patId = 1L; // replace with a valid ID
        given()
                .pathParam("patId", patId)
                .when()
                    .delete("/patHistory/{patId}")
                .then()
                    .statusCode(204); // expecting HTTP 204 No Content
    }

    @Test
    public void testCreate() {

        given()
                .contentType(ContentType.JSON)
                .body("{ \"patId\": 1, \"note\": \"Notes 1\" }")
                .when()
                    .post("/patHistory/add")
                .then()
                    .statusCode(201) // expecting HTTP 201 Created
                    .contentType(ContentType.JSON); // expecting JSON response content

        // find the new saved patient
        given()
                //Returning floats and doubles as BigDecimal
                .contentType(ContentType.JSON)
                .pathParam("patId", 1)
                .when()
                    .get("/patHistory/{patId}")
                .then()
                    .statusCode(200)
                    .contentType(ContentType.JSON)
                    .body(
                            // Check for existence of "patId" with Hamcrest matcher
                            "$", hasKey("patId"),
                            // Check for existence of "notes" with Hamcrest matcher
                            "$", hasKey("notes"),
                            // Check for "patId" with expected value
                            "patId", equalTo(1),
                            // Check for "notes" with expected value
                            "notes[0]", equalTo("Note Test 1"),
                            "notes[1]", equalTo("Notes 1")
                    );
        
    }


}