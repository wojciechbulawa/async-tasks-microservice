package com.example.async.tasks.service.sub;

import com.example.async.tasks.service.ProcessedText;
import lombok.NonNull;

import java.util.Comparator;

public record SubTaskResult(@NonNull SubTask subTask,
                            int typos) implements Comparable<SubTaskResult> {

    private static final Comparator<SubTaskResult> COMPARATOR = Comparator
            .comparing(SubTaskResult::typos)
            .thenComparing(result -> result.subTask.startIndex());

    public ProcessedText toProcessedText() {
        return new ProcessedText(subTask.startIndex(), typos);
    }

    @Override
    public int compareTo(SubTaskResult other) {
        return COMPARATOR.compare(this, other);
    }
}
