package de.daxu.guice.config;

import de.daxu.guice.config.testing.ComplexObject;
import de.daxu.guice.config.testing.ExternalTestConfiguration;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class ConfigurationResolverTest {

    @Test
    public void resolve() throws Exception {
        ConfigurationResolver resolver = new ConfigurationResolver();

        ExternalTestConfiguration instance = resolver.resolve(ExternalTestConfiguration.class);

        assertThat(instance.getApplicationName())
                .isEqualTo("test");
        assertThat(instance.getListOfStrings())
                .containsExactly("1", "2");
        assertThat(instance.getComplexObject())
                .isEqualTo(new ComplexObject("test"));
        assertThat(instance.getListOfComplexObjects())
                .containsExactly(
                        new ComplexObject("1"),
                        new ComplexObject("2"),
                        new ComplexObject("3"));
    }

    @Test
    public void resolve_invalidJsonContent() throws Exception {
        ConfigurationResolver resolver = new ConfigurationResolver();

        assertThatThrownBy(() -> resolver.resolve(TestConfigurationWithInvalidJsonContent.class))
                .isInstanceOf(ExternalConfigurationException.class);
    }

    @Test
    public void resolve_nonExistentJsonFile() throws Exception {
        ConfigurationResolver resolver = new ConfigurationResolver();

        assertThatThrownBy(() -> resolver.resolve(TestConfigurationWithNonExistentJsonFile.class))
                .isInstanceOf(ExternalConfigurationException.class);
    }

    @Configuration(location = "invalid-test-application.json", external = true)
    private class TestConfigurationWithInvalidJsonContent { }

    @Configuration(location = "non-existent-test-application.json", external = true)
    private class TestConfigurationWithNonExistentJsonFile { }
}