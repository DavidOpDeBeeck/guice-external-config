package de.daxu.guice.config.fileloader;

import de.daxu.guice.config.ExternalConfigurationException;

import java.io.InputStream;

import static java.lang.String.format;

public class SystemResourceLoader implements FileLoader {

    private static final String FILE_NOT_FOUND = "System resource with path %s was not found";

    @Override
    public InputStream load(String relativePath) {
        InputStream inputStream = ClassLoader.getSystemResourceAsStream(relativePath);

        if (inputStream == null) {
            throw new ExternalConfigurationException(format(FILE_NOT_FOUND, relativePath));
        }

        return inputStream;
    }
}
