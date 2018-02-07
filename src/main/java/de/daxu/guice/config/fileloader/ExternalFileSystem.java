package de.daxu.guice.config.fileloader;

import de.daxu.guice.config.ExternalConfigurationException;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

import static java.lang.String.format;
import static java.nio.file.Files.newBufferedWriter;
import static java.nio.file.StandardOpenOption.APPEND;

public class ExternalFileSystem implements FileSystem {

    private static final String FILE_NOT_FOUND = "External file with path %s was not found";
    private static final String IO_ERROR = "Failed accessing file with path %s";

    @Override
    public boolean exists(String relativePath) {
        return new File(relativePath).exists();
    }

    @Override
    public void createFile(String relativePath) {
        try {
            if (!new File(relativePath).createNewFile()) {
                throw new ExternalConfigurationException(format(IO_ERROR, relativePath));
            }
        } catch (IOException e) {
            throw new ExternalConfigurationException(format(IO_ERROR, relativePath), e);
        }
    }

    @Override
    public void writeToFile(String relativePath, String content) {
        Path filePath = new File(relativePath).toPath();
        try (BufferedWriter writer = newBufferedWriter(filePath, APPEND)) {
            writer.write(format("%s\n", content));
        } catch (IOException e) {
            throw new ExternalConfigurationException(format(IO_ERROR, relativePath), e);
        }
    }

    @Override
    public InputStream asInputStream(String relativePath) {
        try {
            return new FileInputStream(new File(relativePath));
        } catch (FileNotFoundException e) {
            String message = format(FILE_NOT_FOUND, relativePath);
            throw new ExternalConfigurationException(message, e);
        }
    }
}
