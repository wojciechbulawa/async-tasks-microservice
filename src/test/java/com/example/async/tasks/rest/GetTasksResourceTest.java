package com.example.async.tasks.rest;

import com.example.async.tasks.dto.TaskDto;
import com.example.async.tasks.dto.TaskRequestDto;
import com.example.async.tasks.dto.TaskResponseDto;
import com.example.async.tasks.entity.Status;
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

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

@IntegrationTest
class GetTasksResourceTest {

    private static final BigDecimal ZERO = BigDecimal.valueOf(0.00)
            .setScale(2);

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
        String pattern = "BCD";
        String input = "ABCD";
        Long id = postNewTask(pattern, input);
        waiter.awaitTaskStarted(id);

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
                .status(Status.STARTED.name())
                .percentage(ZERO)
                .position(null)
                .typos(null)
                .pattern(pattern)
                .input(input)
                .build());
    }

    @Test
    void shouldReturnNotFound_whenRequestsNonExistingTask() {
        // given
        long id = 2783912973L;

        // when
        ExtractableResponse<Response> response = getNewTask(id);

        // then
        assertThat(response.statusCode())
                .isEqualTo(HttpStatus.NOT_FOUND.value());
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
