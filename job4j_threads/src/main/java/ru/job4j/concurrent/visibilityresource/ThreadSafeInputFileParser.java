package ru.job4j.concurrent.visibilityresource;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.function.Predicate;

public final class ThreadSafeInputFileParser {

    private final File file;

    public ThreadSafeInputFileParser(final File file) {
        this.file = new File(file.toURI());
    }

    public synchronized String getContent() throws IOException {
        return getContent(x -> true);
    }

    public synchronized String getContentWithoutUnicode() throws IOException {
        return getContent(x -> x < 0x80);
    }

    private synchronized String getContent(Predicate<Character> filter) throws IOException {
        StringBuilder output = new StringBuilder();
        try (BufferedInputStream in = new BufferedInputStream(new FileInputStream(file))) {
            int data;
            while ((data = in.read()) != -1) {
                char out = (char) data;
                if (filter.test(out)) {
                    output.append(out);
                }
            }
        }
        return output.toString();
    }
}
