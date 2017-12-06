package de.daxu.guice.config.testing;

import javax.inject.Inject;
import javax.inject.Named;

@Named
public class Component {

    private final TestConfiguration configuration;

    @Inject
    public Component(TestConfiguration configuration) {
        this.configuration = configuration;
    }

    public TestConfiguration getConfiguration() {
        return configuration;
    }
}