package com.example.async.tasks.service;

import com.example.async.tasks.service.sub.SubTask;
import lombok.Getter;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@Getter
public class TaskSplitter {

    private static final BigDecimal HUNDRED = BigDecimal.valueOf(100);

    private final ProcessingTask mainTask;
    private final BigDecimal subTaskPercentagePoints;

    public TaskSplitter(ProcessingTask mainTask) {
        this.mainTask = mainTask;
        this.subTaskPercentagePoints = getSubTaskPercentagePoints(mainTask);
    }

    public Stream<SubTask> split() {
        int wordLength = mainTask.pattern().length();
        int maxStartIndex = mainTask.text().length() - wordLength;
        return IntStream.iterate(0,
                        (startIndex) -> startIndex <= maxStartIndex,
                        start -> start + 1)
                .mapToObj(start -> createSubTask(wordLength, start));
    }

    private static BigDecimal getSubTaskPercentagePoints(ProcessingTask task) {
        BigDecimal size = BigDecimal.valueOf(size(task));
        return HUNDRED
                .divide(size, 2, RoundingMode.DOWN);
    }

    private static int size(ProcessingTask task) {
        return task.text().length() - task.pattern().length();
    }

    private SubTask createSubTask(int wordLength, int start) {
        int end = wordLength + start;
        String substring = mainTask.text().substring(start, end);
        return new SubTask(start, mainTask.pattern(), substring);
    }
}
