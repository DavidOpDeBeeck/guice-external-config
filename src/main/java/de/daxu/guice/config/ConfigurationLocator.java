package de.daxu.guice.config;

import org.reflections.Reflections;

import java.util.Set;

class ConfigurationLocator {

    private final Reflections reflections;

    ConfigurationLocator(String... packages) {
        this.reflections = new Reflections((Object[]) packages);
    }

    Set<Class<?>> findAllConfigurations() {
        return reflections.getTypesAnnotatedWith(Configuration.class);
    }
}
