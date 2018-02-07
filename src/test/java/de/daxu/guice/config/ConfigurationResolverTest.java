package de.daxu.guice.config;

import com.google.common.collect.ImmutableMap;
import de.daxu.guice.config.testing.ComplexObject;
import de.daxu.guice.config.testing.ExternalTestConfiguration;
import org.junit.After;
import org.junit.Test;

import java.io.File;

import static com.google.common.collect.ImmutableMap.of;
import static java.util.Collections.emptyMap;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class ConfigurationResolverTest {

    private static final String DEFAULT_VALUE = "defaultValue";
    private static final String NON_EXISTENT_CONFIG_LOCATION = "test/non-existent-test-application.json";

    @After
    public void tearDown() throws Exception {
        File configFile = new File(NON_EXISTENT_CONFIG_LOCATION);
        configFile.delete();
    }

    @Test
    public void resolve() throws Exception {
        ConfigurationResolver resolver = new ConfigurationResolver(emptyMap());

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
        ConfigurationResolver resolver = new ConfigurationResolver(emptyMap());

        assertThatThrownBy(() -> resolver.resolve(TestConfigurationWithInvalidJsonContent.class))
                .isInstanceOf(ExternalConfigurationException.class);
    }

    @Test
    public void resolve_nonExistentJsonFile() throws Exception {
        ConfigurationResolver resolver = new ConfigurationResolver(emptyMap());

        assertThatThrownBy(() -> resolver.resolve(TestConfigurationWithNonExistentJsonFile.class))
                .isInstanceOf(ExternalConfigurationException.class);
    }

    @Test
    public void resolve_nonExistentJsonFile_withTemplateMapping() throws Exception {
        ImmutableMap<Class<?>, Object> templateMap = of(TestConfigurationWithNonExistentJsonFile.class, template());
        ConfigurationResolver resolver = new ConfigurationResolver(templateMap);

        TestConfigurationWithNonExistentJsonFile config = resolver.resolve(TestConfigurationWithNonExistentJsonFile.class);

        assertThat(config.getField())
                .isEqualTo(DEFAULT_VALUE);
    }

    private TestConfigurationWithNonExistentJsonFile template() {
        return new TestConfigurationWithNonExistentJsonFile(DEFAULT_VALUE);
    }

    @Configuration(location = "test/invalid-test-application.json", external = true)
    private class TestConfigurationWithInvalidJsonContent {
    }

    @Configuration(location = NON_EXISTENT_CONFIG_LOCATION, external = true)
    private static class TestConfigurationWithNonExistentJsonFile {

        private String field;

        @SuppressWarnings("unused")
        public TestConfigurationWithNonExistentJsonFile() {
            // necessary for jackson
        }

        TestConfigurationWithNonExistentJsonFile(String field) {
            this.field = field;
        }

        public String getField() {
            return field;
        }
    }
}