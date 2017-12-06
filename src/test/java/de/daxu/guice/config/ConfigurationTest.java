package de.daxu.guice.config;

import com.google.inject.Injector;
import com.google.inject.Module;
import de.daxu.guice.config.testing.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static com.google.inject.Guice.createInjector;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.runners.Parameterized.*;

@RunWith(Parameterized.class)
public class ConfigurationTest {

    private static final String TESTING_PACKAGE = "de.daxu.guice.config.testing";

    @Parameters(name = "{0}")
    public static Collection<Object[]> data() {
        return Arrays.asList(new Class<?>[][] {
                { InternalTestConfiguration.class },
                { ExternalTestConfiguration.class }
        });
    }

    private final Injector injector;
    private TestConfiguration configuration;

    public ConfigurationTest(Class<TestConfiguration> configurationClass) {
        this.injector = createInjector(new ExternalConfigModule(TESTING_PACKAGE), createModule(configurationClass));
    }

    @Before
    public void setUp() throws Exception {
        this.configuration = injector.getInstance(Component.class).getConfiguration();
    }

    @Test
    public void canHandleStringProperties() throws Exception {
        assertThat(configuration.getApplicationName())
                .isEqualTo("test");
    }

    @Test
    public void canHandleListOfStringsProperties() throws Exception {
        assertThat(configuration.getListOfStrings())
                .containsExactly("1", "2");
    }

    @Test
    public void canHandleComplexObjectProperties() throws Exception {
        assertThat(configuration.getComplexObject())
                .isEqualTo(new ComplexObject("test"));
    }

    @Test
    public void canHandleListOfComplexObjectsProperties() throws Exception {
        assertThat(configuration.getListOfComplexObjects())
                .containsExactly(
                        new ComplexObject("1"),
                        new ComplexObject("2"),
                        new ComplexObject("3"));
    }

    private Module createModule(Class<TestConfiguration> configurationClass) {
        return binder -> binder.bind(TestConfiguration.class).to(configurationClass);
    }
}
