package ru.job4j.concurrent.visibilityresource;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public final class ThreadSafeOutputFileParser {

    private final File file;

    public ThreadSafeOutputFileParser(final File file) {
        this.file = new File(file.toURI());
    }

    public synchronized void saveContent(String content) throws IOException {
        try (BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(file))) {
            for (int i = 0; i < content.length(); i += 1) {
                out.write(content.charAt(i));
            }
        }
    }
}
