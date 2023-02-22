package com.example.async.tasks.service;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

import java.util.Comparator;
import java.util.SortedSet;
import java.util.TreeSet;

@Slf4j
public class SimpleTextProcessor implements TextProcessor {

    @Override
    public ProcessedText process(String pattern, String text) {
        SortedSet<Result> results = new TreeSet<>();
        int wordLength = pattern.length();
        for (int start = 0, end = wordLength; end <= text.length(); start++, end++) {
            String substring = text.substring(start, end);
            int typos = calculate(pattern, substring);
            results.add(new Result(typos, start, substring));
        }

        Result first = results.first();
        log.info("Result {} was picked as best match", first);
        return first.toProcessedText();
    }

    private int calculate(String pattern, String text) {
        int typos = 0;
        for (int index = 0; index < text.length(); index++) {
            if (isTypo(text.charAt(index), pattern.charAt(index))) {
                typos++;
            }
        }

        return typos;
    }

    boolean isTypo(char a, char b) {
        return a != b;
    }

    private record Result(int typos,
                          int position,
                          @NonNull String text) implements Comparable<Result> {

        private static final Comparator<Result> COMPARATOR = Comparator
                .comparing(Result::typos)
                .thenComparing(Result::position)
                .thenComparing(Result::text);

        ProcessedText toProcessedText() {
            return new ProcessedText(position, typos);
        }

        @Override
        public int compareTo(Result other) {
            return COMPARATOR.compare(this, other);
        }
    }
}
