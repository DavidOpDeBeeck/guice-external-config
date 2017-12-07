package de.daxu.guice.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.daxu.guice.config.fileloader.FileLoader;
import de.daxu.guice.config.fileloader.FileLoaderFactory;

import java.io.IOException;
import java.io.InputStream;

import static com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES;
import static java.lang.String.format;

class ConfigurationResolver {

    private static final String MAPPING_FAILED = "Failed to convert configuration file to %s";

    private final ObjectMapper objectMapper;
    private final FileLoaderFactory fileLoaderFactory;

    ConfigurationResolver() {
        this.objectMapper = createObjectMapper();
        this.fileLoaderFactory = new FileLoaderFactory();
    }

    <T> T resolve(Class<T> configurationClass) {
        InputStream jsonFile = findJsonFile(configurationClass);

        return mapToInstance(configurationClass, jsonFile);
    }

    private InputStream findJsonFile(Class<?> configurationClass) {
        Configuration annotation = configurationClass.getAnnotation(Configuration.class);
        FileLoader fileLoader = fileLoaderFactory.create(annotation.external());

        return fileLoader.load(annotation.location());
    }

    private <T> T mapToInstance(Class<T> configurationClass, InputStream configurationFile) {
        try {
            return objectMapper.readValue(configurationFile, configurationClass);
        } catch (IOException e) {
            throw new ExternalConfigurationException(format(MAPPING_FAILED, configurationClass.getSimpleName()), e);
        }
    }

    private ObjectMapper createObjectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(FAIL_ON_UNKNOWN_PROPERTIES, false);
        return objectMapper;
    }
}
