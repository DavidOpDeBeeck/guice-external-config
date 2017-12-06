package de.daxu.guice.config.testing;

import de.daxu.guice.config.Configuration;

import java.util.List;

@Configuration(location = "test-application.json")
public class InternalTestConfiguration implements TestConfiguration {

    @SuppressWarnings("unused")
    private String applicationName;
    @SuppressWarnings("unused")
    private List<String> listOfStrings;
    @SuppressWarnings("unused")
    private ComplexObject complexObject;
    @SuppressWarnings("unused")
    private List<ComplexObject> listOfComplexObjects;

    @Override
    public String getApplicationName() {
        return applicationName;
    }

    @Override
    public List<String> getListOfStrings() {
        return listOfStrings;
    }

    @Override
    public ComplexObject getComplexObject() {
        return complexObject;
    }

    @Override
    public List<ComplexObject> getListOfComplexObjects() {
        return listOfComplexObjects;
    }
}
