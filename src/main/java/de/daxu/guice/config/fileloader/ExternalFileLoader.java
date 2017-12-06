package de.daxu.guice.config.fileloader;

import de.daxu.guice.config.ExternalConfigurationException;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import static java.lang.String.format;

public class ExternalFileLoader implements FileLoader {

    private static final String FILE_NOT_FOUND = "External file with path %s was not found";

    @Override
    public InputStream load(String relativePath) {
        try {
            return new FileInputStream(new File(relativePath));
        } catch (java.io.FileNotFoundException e) {
            String message = format(FILE_NOT_FOUND, relativePath);
            throw new ExternalConfigurationException(message, e);
        }
    }
}
