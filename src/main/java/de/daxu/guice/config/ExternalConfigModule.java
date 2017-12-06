package de.daxu.guice.config;

import com.google.inject.AbstractModule;

public class ExternalConfigModule extends AbstractModule {

    private final ConfigurationLocator locator;
    private final ConfigurationResolver resolver;

    public ExternalConfigModule(String... packagesToScan) {
        this.locator = new ConfigurationLocator(packagesToScan);
        this.resolver = new ConfigurationResolver();
    }

    @Override
    @SuppressWarnings("unchecked")
    protected void configure() {
        locator.findAllConfigurations()
                .stream()
                .map(aClass -> (Class<Object>) aClass)
                .forEach(this::bindConfigurationClass);
    }

    private void bindConfigurationClass(Class<Object> configurationClass) {
        Object instance = resolver.resolve(configurationClass);
        bind(configurationClass).toInstance(instance);
    }
}
