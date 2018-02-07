package de.daxu.guice.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import de.daxu.guice.config.fileloader.FileSystem;
import de.daxu.guice.config.fileloader.FileSystemFactory;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import static com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES;
import static com.fasterxml.jackson.databind.SerializationFeature.INDENT_OUTPUT;
import static java.lang.String.format;
import static java.nio.charset.Charset.defaultCharset;

class ConfigurationResolver {

    private static final String MAPPING_FAILED = "Failed to convert configuration file to %s";
    private static final String TEMPLATE_FAILED = "Failed to write template to configuration file %s";
    private static final String LOCATOR_FAILED = "Failed to locate config file for configuration %s";

    private final ObjectMapper objectMapper;
    private final FileSystemFactory fileSystemFactory;
    private final Map<Class<?>, Object> configurationTemplateMap;

    ConfigurationResolver(Map<Class<?>, Object> configurationTemplateMap) {
        this.objectMapper = createObjectMapper();
        this.fileSystemFactory = new FileSystemFactory();
        this.configurationTemplateMap = configurationTemplateMap;
    }

    <T> T resolve(Class<T> configurationClass) {
        InputStream jsonFile = findJsonFile(configurationClass);

        return mapToInstance(configurationClass, jsonFile);
    }

    private InputStream findJsonFile(Class<?> configurationClass) {
        Configuration annotation = configurationClass.getAnnotation(Configuration.class);
        FileSystem fileSystem = fileSystemFactory.create(annotation.external());
        String configLocation = annotation.location();

        if (fileSystem.exists(configLocation)) {
            return fileSystem.asInputStream(configLocation);
        }

        if (hasTemplateDefined(configurationClass)) {
            return writeTemplateToFile(fileSystem, configLocation, configurationClass);
        }

        throw new ExternalConfigurationException(format(LOCATOR_FAILED, configurationClass.getSimpleName()));
    }

    private boolean hasTemplateDefined(Class<?> configurationClass) {
        return configurationTemplateMap.containsKey(configurationClass);
    }

    private InputStream writeTemplateToFile(FileSystem fileSystem, String configLocation, Class<?> configurationClass) {
        try {
            String template = objectMapper.writeValueAsString(configurationTemplateMap.get(configurationClass));
            fileSystem.createFile(configLocation);
            fileSystem.writeToFile(configLocation, template);
            return new ByteArrayInputStream(template.getBytes(defaultCharset()));
        } catch (JsonProcessingException e) {
            throw new ExternalConfigurationException(format(TEMPLATE_FAILED, configurationClass.getSimpleName()), e);
        }
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
        objectMapper.enable(INDENT_OUTPUT);
        return objectMapper;
    }
}
