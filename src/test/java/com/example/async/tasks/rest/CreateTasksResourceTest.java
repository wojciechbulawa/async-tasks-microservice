package com.example.async.tasks.rest;

import com.example.async.tasks.dto.TaskRequestDto;
import com.example.async.tasks.dto.TaskResponseDto;
import com.example.async.tasks.utils.AwaitHelper;
import com.example.async.tasks.utils.Credentials;
import com.example.async.tasks.utils.IntegrationTest;
import com.example.async.tasks.utils.TestUsers;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@IntegrationTest
class CreateTasksResourceTest {

    @Autowired
    private AwaitHelper waiter;

    private Credentials user;

    @BeforeEach
    void setUp() {
        user = TestUsers.getUser();
    }

    @Test
    void shouldReturnId_whenTaskCreated() {
        // given
        TaskRequestDto body = TaskRequestDto.builder()
                .input("ABCD")
                .pattern("BCD")
                .build();

        // when
        ExtractableResponse<Response> response = postNewTask(body);
        waiter.awaitTasksStarted(1);

        // then
        assertThat(response.statusCode())
                .isEqualTo(HttpStatus.CREATED.value());
        assertThat(response.contentType())
                .isEqualTo(MediaType.APPLICATION_JSON_VALUE);
        assertThat(response.body())
                .isNotNull();
        TaskResponseDto responseBody = response.body().as(TaskResponseDto.class);
        assertThat(responseBody.getId())
                .isNotNull();
    }

    @Test
    void shouldReturnBadRequest_whenInputIsBlank() {
        // given
        TaskRequestDto body = TaskRequestDto.builder()
                .input("   ")
                .pattern("BCD")
                .build();

        // when
        ExtractableResponse<Response> response = postNewTask(body);

        // then
        assertThat(response.statusCode())
                .isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(response.contentType())
                .isEqualTo(MediaType.APPLICATION_JSON_VALUE);
        assertThat(response.body())
                .isNotNull();
        List<String> errors = response.body().jsonPath().getList("errors");
        assertThat(errors).contains("Field 'input' must not be blank");
    }

    @Test
    void shouldReturnBadRequest_whenPatternIsBlank() {
        // given
        TaskRequestDto body = TaskRequestDto.builder()
                .input("ABCD")
                .pattern("   ")
                .build();

        // when
        ExtractableResponse<Response> response = postNewTask(body);

        // then
        assertThat(response.statusCode())
                .isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(response.contentType())
                .isEqualTo(MediaType.APPLICATION_JSON_VALUE);
        assertThat(response.body())
                .isNotNull();
        List<String> errors = response.body().jsonPath().getList("errors");
        assertThat(errors).contains("Field 'pattern' must not be blank");
    }

    private ExtractableResponse<Response> postNewTask(TaskRequestDto body) {
        return RestAssured.given()
                .auth()
                .preemptive()
                .basic(user.username(), user.password())
                .contentType(ContentType.JSON)
                .body(body)
                .when().post("/api/tasks/create")
                .then()
                .extract();
    }

}
