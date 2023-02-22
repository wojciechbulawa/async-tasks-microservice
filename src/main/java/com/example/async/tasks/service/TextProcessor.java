package com.example.async.tasks.service;

public interface TextProcessor {

    ProcessedText process(String pattern, String text);
}
