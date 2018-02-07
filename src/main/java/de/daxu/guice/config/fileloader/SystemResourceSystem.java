package de.daxu.guice.config.fileloader;

import de.daxu.guice.config.ExternalConfigurationException;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;

import static java.lang.String.format;
import static java.nio.file.Files.newBufferedWriter;
import static java.nio.file.StandardOpenOption.APPEND;

public class SystemResourceSystem implements FileSystem {

    private static final String FILE_NOT_FOUND = "System resource with path %s was not found";
    private static final String NOT_SUPPORTED = "File creation and writing is not supported for internal configurations";

    @Override
    public boolean exists(String relativePath) {
        return ClassLoader.getSystemResourceAsStream(relativePath) != null;
    }

    @Override
    public void createFile(String relativePath) {
        throw new ExternalConfigurationException(NOT_SUPPORTED);
    }

    @Override
    public void writeToFile(String relativePath, String content) {
        throw new ExternalConfigurationException(NOT_SUPPORTED);
    }

    @Override
    public InputStream asInputStream(String relativePath) {
        InputStream inputStream = ClassLoader.getSystemResourceAsStream(relativePath);

        if (inputStream == null) {
            throw new ExternalConfigurationException(format(FILE_NOT_FOUND, relativePath));
        }

        return inputStream;
    }
}
