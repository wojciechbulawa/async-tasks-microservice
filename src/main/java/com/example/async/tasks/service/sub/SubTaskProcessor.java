package com.example.async.tasks.service.sub;

class SubTaskProcessor {

    SubTaskResult process(SubTask subTask) {
        int typos = calculate(subTask.pattern(), subTask.text());
        return new SubTaskResult(subTask, typos);
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

    private boolean isTypo(char a, char b) {
        return a != b;
    }
}
