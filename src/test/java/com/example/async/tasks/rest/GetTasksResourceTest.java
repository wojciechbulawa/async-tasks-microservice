package com.example.async.tasks.rest;

import com.example.async.tasks.dto.TaskDto;
import com.example.async.tasks.dto.TaskRequestDto;
import com.example.async.tasks.dto.TaskResponseDto;
import com.example.async.tasks.entity.TaskStatus;
import com.example.async.tasks.utils.Credentials;
import com.example.async.tasks.utils.IntegrationTest;
import com.example.async.tasks.utils.TestUsers;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import static org.assertj.core.api.Assertions.assertThat;

@IntegrationTest
class GetTasksResourceTest {

    private Credentials user;

    @BeforeEach
    void setUp() {
        user = TestUsers.getUser();
    }

    @Test
    void shouldReturnId_whenTaskCreated() {
        // given
        String pattern = "BCD";
        String input = "ABCD";
        Long id = postNewTask(pattern, input);

        // when
        ExtractableResponse<Response> response = getNewTask(id);

        // then
        assertThat(response.statusCode())
                .isEqualTo(HttpStatus.OK.value());
        assertThat(response.contentType())
                .isEqualTo(MediaType.APPLICATION_JSON_VALUE);
        assertThat(response.body())
                .isNotNull();
        TaskDto taskDto = response.body().as(TaskDto.class);
        assertThat(taskDto).isEqualTo(TaskDto.builder()
                .id(id)
                .status(TaskStatus.RECEIVED.name())
                .percentage(0)
                .position(null)
                .typos(null)
                .pattern(null)
                .input(null)
                .build());
    }

    private ExtractableResponse<Response> getNewTask(Long id) {
        return RestAssured.given()
                .auth()
                .preemptive()
                .basic(user.username(), user.password())
                .when().get("/api/tasks/" + id)
                .then()
                .extract();
    }

    private Long postNewTask(String pattern, String input) {
        TaskRequestDto body = TaskRequestDto.builder()
                .pattern(pattern)
                .input(input)
                .build();

        return RestAssured.given()
                .auth()
                .preemptive()
                .basic(user.username(), user.password())
                .contentType(ContentType.JSON)
                .body(body)
                .when().post("/api/tasks/create")
                .then()
                .statusCode(HttpStatus.CREATED.value())
                .extract().as(TaskResponseDto.class)
                .getId();
    }

}
