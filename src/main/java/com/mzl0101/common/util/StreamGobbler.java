package com.mzl0101.common.util;

import lombok.SneakyThrows;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.function.Consumer;

public class StreamGobbler implements Runnable {
    private InputStream inputStream;
    private Consumer<String> consumer;

    public StreamGobbler(InputStream inputStream, Consumer<String> consumer) {
        this.inputStream = inputStream;
        this.consumer = consumer;
    }

    @SneakyThrows
    @Override
    public void run() {
        new BufferedReader(new InputStreamReader(inputStream, "gbk")).lines()
                .forEach(consumer);
    }
}