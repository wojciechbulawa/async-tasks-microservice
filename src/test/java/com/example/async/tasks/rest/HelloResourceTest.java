package com.example.async.tasks.rest;

import com.example.async.tasks.dto.HelloMessage;
import com.example.async.tasks.utils.Credentials;
import com.example.async.tasks.utils.IntegrationTest;
import com.example.async.tasks.utils.TestUsers;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import static org.assertj.core.api.Assertions.assertThat;

@IntegrationTest
class HelloResourceTest {

    private Credentials user;
    private Credentials admin;

    @BeforeEach
    void setUp() {
        user = TestUsers.getUser();
        admin = TestUsers.getAdmin();
    }

    @Test
    void shouldRespond_whenUserIsNotLoggedIn_andResourceIsNotSecured() {
        // given
        String expectedMsg = "Hello";

        // when
        HelloMessage message = RestAssured.given()
                .when().get("/api/hello/non-logged-in")
                .then()
                .statusCode(HttpStatus.OK.value())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .extract()
                .as(HelloMessage.class);

        // then
        assertThat(message.getMessage()).isEqualTo(expectedMsg);
    }

    @Test
    void shouldRespondWithUnauthorized_whenNotIsLoggedIn_andResourceIsSecured() {
        RestAssured.given()
                .when().get("/api/hello/logged-in")
                .then()
                .statusCode(HttpStatus.UNAUTHORIZED.value());
    }

    @Test
    void shouldRespond_whenUserIsLoggedIn_andResourceIsNotSecured() {
        // given
        String expectedMsg = "Hello";

        // when
        HelloMessage message = RestAssured.given()
                .auth()
                .preemptive()
                .basic(user.username(), user.password())
                .when().get("/api/hello/non-logged-in")
                .then()
                .statusCode(HttpStatus.OK.value())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .extract()
                .as(HelloMessage.class);

        // then
        assertThat(message.getMessage()).isEqualTo(expectedMsg);
    }

    @Test
    void shouldRespond_whenUserIsLoggedIn_andResourceIsSecured() {
        // given
        String expectedMsg = "Hello logged-in user";

        // when
        HelloMessage message = RestAssured.given()
                .auth()
                .basic(user.username(), user.password())
                .when().get("/api/hello/logged-in")
                .then()
                .statusCode(HttpStatus.OK.value())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .extract()
                .as(HelloMessage.class);

        // then
        assertThat(message.getMessage()).isEqualTo(expectedMsg);
    }

    @Test
    void shouldRespondWithUnauthorized_whenUserIsLoggedIn_andResourceIsAuthorizedOnlyForAdmin() {
        RestAssured.given()
                .auth()
                .basic(user.username(), user.password())
                .when().get("/api/hello/admin")
                .then()
                .statusCode(HttpStatus.FORBIDDEN.value());
    }

    @Test
    void shouldRespond_whenAdminIsLoggedIn_andResourceIsAuthorizedOnlyForAdmin() {
        // given
        String expectedMsg = "Hello admin";

        // when
        HelloMessage message = RestAssured.given()
                .auth()
                .basic(admin.username(), admin.password())
                .when().get("/api/hello/admin")
                .then()
                .statusCode(HttpStatus.OK.value())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .extract()
                .as(HelloMessage.class);

        // then
        assertThat(message.getMessage()).isEqualTo(expectedMsg);
    }
}
