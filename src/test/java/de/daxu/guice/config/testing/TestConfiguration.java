package de.daxu.guice.config.testing;

import java.util.List;

public interface TestConfiguration {

    String getApplicationName();

    List<String> getListOfStrings();

    ComplexObject getComplexObject();

    List<ComplexObject> getListOfComplexObjects();
}
