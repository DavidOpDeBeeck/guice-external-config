package de.daxu.guice.config;

import org.reflections.Reflections;

import java.util.Set;

class ConfigurationLocator {

    private final Reflections reflections;

    ConfigurationLocator(Set<String> packages) {
        this.reflections = new Reflections(packages);
    }

    Set<Class<?>> findAllConfigurations() {
        return reflections.getTypesAnnotatedWith(Configuration.class);
    }
}
