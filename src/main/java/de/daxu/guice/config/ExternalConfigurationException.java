package de.daxu.guice.config;

public class ExternalConfigurationException extends RuntimeException {

    public ExternalConfigurationException(String message) {
        super(message);
    }

    public ExternalConfigurationException(String message, Throwable cause) {
        super(message, cause);
    }
}
