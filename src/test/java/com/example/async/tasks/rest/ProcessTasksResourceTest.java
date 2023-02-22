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
class ProcessTasksResourceTest {

    private static final BigDecimal HUNDRED = BigDecimal.valueOf(100.00)
            .setScale(2);

    @Autowired
    private AwaitHelper waiter;

    private Credentials user;

    @BeforeEach
    void setUp() {
        user = TestUsers.getUser();
    }

    @Test
    void shouldReturn_position1_typos0_whenInputABCD_andPatternBCD() {
        // given
        String pattern = "BCD";
        String input = "ABCD";

        // when
        Long id = postNewTask(pattern, input);
        waiter.awaitTaskCompleted(id);
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
                .status(Status.COMPLETED.name())
                .percentage(HUNDRED)
                .position(1)
                .typos(0)
                .pattern(pattern)
                .input(input)
                .build());
    }

    @Test
    void shouldReturn_position1_typos1_whenInputABCD_andPatternBWD() {
        // given
        String pattern = "BWD";
        String input = "ABCD";

        // when
        Long id = postNewTask(pattern, input);
        waiter.awaitTaskCompleted(id);
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
                .status(Status.COMPLETED.name())
                .percentage(HUNDRED)
                .position(1)
                .typos(1)
                .pattern(pattern)
                .input(input)
                .build());
    }

    @Test
    void shouldReturn_position4_typos1_whenInputABCDEFG_andPatternCFG() {
        // given
        String pattern = "CFG";
        String input = "ABCDEFG";

        // when
        Long id = postNewTask(pattern, input);
        waiter.awaitTaskCompleted(id);
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
                .status(Status.COMPLETED.name())
                .percentage(HUNDRED)
                .position(4)
                .typos(1)
                .pattern(pattern)
                .input(input)
                .build());
    }

    @Test
    void shouldReturn_position0_typos0_whenInputABCABC_andPatternABC() {
        // given
        String pattern = "ABC";
        String input = "ABCABC";

        // when
        Long id = postNewTask(pattern, input);
        waiter.awaitTaskCompleted(id);
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
                .status(Status.COMPLETED.name())
                .percentage(HUNDRED)
                .position(0)
                .typos(0)
                .pattern(pattern)
                .input(input)
                .build());
    }

    @Test
    void shouldReturn_position1_typos2_whenInputABCDEFG_andPatternTDD() {
        // given
        String pattern = "TDD";
        String input = "ABCDEFG";

        // when
        Long id = postNewTask(pattern, input);
        waiter.awaitTaskCompleted(id);
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
                .status(Status.COMPLETED.name())
                .percentage(HUNDRED)
                .position(1)
                .typos(2)
                .pattern(pattern)
                .input(input)
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
