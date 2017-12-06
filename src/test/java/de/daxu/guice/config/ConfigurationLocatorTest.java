package de.daxu.guice.config;

import de.daxu.guice.config.testing.ExternalTestConfiguration;
import de.daxu.guice.config.testing.InternalTestConfiguration;
import org.junit.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class ConfigurationLocatorTest {

    @Test
    public void findAllConfigurations() throws Exception {
        ConfigurationLocator locator = new ConfigurationLocator("de.daxu.guice.config.testing");

        Set<Class<?>> configurations = locator.findAllConfigurations();

        assertThat(configurations)
                .containsOnly(ExternalTestConfiguration.class, InternalTestConfiguration.class);
    }
}