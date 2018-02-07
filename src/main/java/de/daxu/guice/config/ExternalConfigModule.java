package de.daxu.guice.config;

import com.google.inject.AbstractModule;

import java.util.*;

import static java.util.Arrays.asList;

public class ExternalConfigModule extends AbstractModule {

    private final ConfigurationLocator locator;
    private final ConfigurationResolver resolver;

    private ExternalConfigModule(Map<Class<?>, Object> configurationTemplateMap, Set<String> packagesToScan) {
        this.locator = new ConfigurationLocator(packagesToScan);
        this.resolver = new ConfigurationResolver(configurationTemplateMap);
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

    public static class Builder {

        private final Map<Class<?>, Object> configurationTemplateMap = new HashMap<>();
        private final Set<String> packagesToScan = new HashSet<>();

        public Builder withTemplate(Class<?> configurationClass, Object template) {
            this.configurationTemplateMap.put(configurationClass, template);
            return this;
        }

        public Builder withPackagesToScan(String ... packagesToScan) {
            this.packagesToScan.addAll(asList(packagesToScan));
            return this;
        }

        public ExternalConfigModule build() {
            return new ExternalConfigModule(configurationTemplateMap, packagesToScan);
        }
    }
}
